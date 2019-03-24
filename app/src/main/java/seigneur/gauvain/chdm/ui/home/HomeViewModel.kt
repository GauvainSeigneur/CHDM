package seigneur.gauvain.chdm.ui.home

import androidx.lifecycle.MutableLiveData
import io.reactivex.rxkotlin.subscribeBy
import seigneur.gauvain.chdm.data.model.exhibition.Exhibition
import seigneur.gauvain.chdm.data.repository.ApiTestRepository
import seigneur.gauvain.chdm.data.repository.ExhibitionRepository
import seigneur.gauvain.chdm.ui.base.BaseViewModel
import timber.log.Timber
import java.util.*

class HomeViewModel(var mExhibitionRepository: ExhibitionRepository) : BaseViewModel() {

    var mListResult = MutableLiveData<ListResult>()

    //List of exhibitions
    private var mExhibitions= ArrayList<Exhibition>()

    var mResponseDate :Date? = null

    /**
     * get Exhibitions
     */
    fun getExhibitions() {
        launch {
            mExhibitionRepository.getExhibitions()
                .flatMapIterable {
                    mResponseDate = it.headers().getDate("Date")
                    Timber.d("header $mResponseDate")
                    it.body()?.exhibitions
                }
                .flatMap {it ->
                    mExhibitions.add(it) //create list of exhibition
                    mExhibitionRepository.getExhibitionObjects(it.id)
                        .doOnNext{
                                inExhibObject -> addObjectsAndIllustration(mExhibitions.indexOf(it), inExhibObject)
                        }
                }
                .subscribeBy(  // named arguments for lambda Subscribers
                    onNext = { println(it) },
                    onError =  { mListResult.value = ListResult(inError = it) },
                    onComplete = {checkDateAndManageList() }
                )
        }
    }

    /**
     * remove item which the end of the exhibition
     * is before the date of the response
     */
    private fun checkDateAndManageList() {
        mExhibitions.forEach {
            val vEndDate:Date? = it.date_end
            val vExhibition = it
            vEndDate?.let {
                if (vEndDate.before(mResponseDate)) {
                    mExhibitions.remove(vExhibition)
                }
            }
            mListResult.value = ListResult(inList = mExhibitions)
        }

    }

    /**
     * Add objects to each exhibition item in the list
     */
    private fun addObjectsAndIllustration(pos:Int, exhibObjects :Exhibition.ExhibitionObjects) {
        //add objects received to each exhibition of the list
        mExhibitions[pos].exhibObjects = exhibObjects
        //add an illustration (if available) to each exhibition of the list
        mExhibitions[pos].illustrationUrl = getAnIllustrationPerExhibition(pos)
    }

    /**
     * Get a image url for and illustration by get a valid url for list of objects
     * injeced in the Exhibitions item
     */
    private fun getAnIllustrationPerExhibition(pos:Int):String? {
        var imageUrl: String? = "No Image Available"
        for (i in 0 until mExhibitions[pos].exhibObjects.objects.size) {
            if (!mExhibitions[pos].exhibObjects.objects[i].imgList.isEmpty()) {
                imageUrl = mExhibitions[pos].exhibObjects.objects[i].firstImageUrl
            }
        }
        return imageUrl
    }

    /**
     * class dedicated to manage UI related data
     */
    data class ListResult(val inList: List<Exhibition>?=null, val inError: Throwable? = null)

}
