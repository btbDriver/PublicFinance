package com.drive.finance.ui.tab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.drive.finance.CreateBounsListFragmentEvent
import com.drive.finance.R
import com.drive.finance.base.BaseFragment
import com.hwangjr.rxbus.RxBus
import com.jude.rollviewpager.RollPagerView
import com.jude.rollviewpager.adapter.LoopPagerAdapter
import org.jetbrains.anko.onClick


class MainFragment : BaseFragment() {

    val rollPagerView by lazy {
        view?.findViewById(R.id.rollPagerView) as RollPagerView
    }
    val bonusListLayout by lazy {
        view?.findViewById(R.id.bonusListLayout) as LinearLayout
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rollPagerView.setAdapter(TestLoopAdapter(rollPagerView))

        bonusListLayout.onClick {
            RxBus.get().post(CreateBounsListFragmentEvent(""))
        }
    }
}

internal class TestLoopAdapter(viewPager: RollPagerView) : LoopPagerAdapter(viewPager) {
    private val imgs = intArrayOf(R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher)

    override fun getView(container: ViewGroup, position: Int): View {
        val view = ImageView(container.context)
        view.setImageResource(imgs[position])
        view.scaleType = ImageView.ScaleType.CENTER_CROP
        view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        return view
    }

    override fun getRealCount(): Int {
        return imgs.size
    }
}

fun createMainFragment() : MainFragment {
    return MainFragment()
}
