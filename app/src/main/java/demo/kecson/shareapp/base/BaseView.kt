package demo.kecson.shareapp.base

/**
 * To do
 * @author      chenKeSheng
 * @date        2017-09-13 18:14
 * @version     V1.0
 */
interface BaseView<in T> {
    fun setPresenter(presenter: T)
}
