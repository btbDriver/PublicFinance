package finance.drive.com.publicfinance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import finance.drive.com.publicfinance.base.BaseFragment

class MainFragment : BaseFragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_main, container, false)
    }

}

fun createMainFragment() : MainFragment {
    return MainFragment()
}
