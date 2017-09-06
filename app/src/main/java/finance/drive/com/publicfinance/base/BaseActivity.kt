package finance.drive.com.publicfinance.base

import android.os.Bundle
import com.hwangjr.rxbus.RxBus
import me.yokeyword.fragmentation.SupportActivity

open class BaseActivity : SupportActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RxBus.get().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        RxBus.get().unregister(this)
    }
}
