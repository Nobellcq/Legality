package zlc.season.rxdownload.demo.list

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.tbruyelle.rxpermissions3.RxPermissions
import kotlinx.android.synthetic.main.activity_demo_list.*
import kotlinx.android.synthetic.main.demo_list_item.*
import zlc.season.rxdownload.demo.R
import zlc.season.rxdownload.demo.manager.DemoManagerActivity
import zlc.season.rxdownload.demo.utils.click
import zlc.season.rxdownload.demo.utils.load
import zlc.season.rxdownload.demo.utils.start
import zlc.season.yasha.linear


class DemoListActivity : AppCompatActivity() {

    private val dataSource by lazy { DemoListDataSource() }

    val rxPermissions = RxPermissions(this) // where this is an Activity or Fragment instance

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_list)
        setSupportActionBar(toolbar)

        renderList()
        rxPermissions
            .request(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_PHONE_STATE
            )
            .subscribe { granted: Boolean ->
                if (granted) {
                    // All requested permissions are granted
                } else {
                    // At least one permission is denied
                }
            }
        rxPermissions
            .request(Manifest.permission.CAMERA)
            .subscribe { granted ->
                Log.d(Companion.TAG, "onCreate: $granted")
                if (granted) { // Always true pre-M
                    // I can control the camera now
                } else {
                    // Oups permission denied
                }
            }
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.task_manager, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_download_manager -> {
                start(DemoManagerActivity::class.java)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun renderList() {
        recycler_view.linear(dataSource) {

            renderItem<DemoListItem> {
                res(R.layout.demo_list_item)

                onBind {
                    tv_name.text = data.name
                    tv_size.text = data.size

                    iv_icon.load(data.icon)

                    btn_pause.click {
                        data.action(containerView.context)
                    }
                }

                onAttach {
                    data.subscribe(btn_pause, containerView.context)
                }

                onDetach {
                    data.dispose()
                }
            }
        }
    }

    companion object {
        private const val TAG = "wdnmd"
    }
}
