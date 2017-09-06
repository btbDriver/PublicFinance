package finance.drive.com.publicfinance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import finance.drive.com.publicfinance.base.BaseFragment

class FinanceFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_finance, container, false)
    }

}

fun createFinanceFragment(): FinanceFragment {
    return FinanceFragment()
}
