package com.example.recicle

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recicle.AndroidVersionsAdapter.onAndroidClickListener
import java.util.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recycle_view)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        val intent: Intent = Intent(this, AndroidVersion::class.java)
        val androidClickListener: onAndroidClickListener = object : onAndroidClickListener {
            override fun onAndroidClick(androidVersions: AndroidVersions?, position: Int) {
                intent.putExtra("name", androidVersions!!.name)
                intent.putExtra("image", androidVersions.image)
                intent.putExtra("version", androidVersions.versions)
                intent.putExtra("info", androidVersions.inform)
                startActivity(intent)
            }
        }
        val adapter: RecyclerView.Adapter<*> = AndroidVersionsAdapter(generateAndroid(), androidClickListener)
        recyclerView.adapter = adapter
    }

    private fun generateAndroid(): List<AndroidVersions> {
        val androidVersions: MutableList<AndroidVersions> = ArrayList()
        androidVersions.add(AndroidVersions("Android 1.5 Cupcake", "https://i.pinimg.com/originals/92/ae/f1/92aef1b15de3c2ec95cc3bae440c91f7.jpg", "1.5", getString(R.string.cupcake)))
        androidVersions.add(AndroidVersions("Android 1.6 Donut", "https://i.pinimg.com/564x/8f/42/cf/8f42cf757116fd7b4511f7a1fa0cb675.jpg", "1.6", getString(R.string.donut)))
        androidVersions.add(AndroidVersions("Android 2.0 Eclair", "https://i.pinimg.com/564x/e2/0b/1f/e20b1f92718824e3817f9bf183688c31.jpg", "2.0-2.1", getString(R.string.eclair)))
        androidVersions.add(AndroidVersions("Android 2.2 Froyo", "https://i.pinimg.com/564x/ae/6f/70/ae6f7081c20cec4a647dbc20f8357c61.jpg", "2.2-2.2.3", getString(R.string.froyo)))
        androidVersions.add(AndroidVersions("Android 2.3 ginger bread", "https://i.pinimg.com/564x/57/75/a4/5775a4a93d3bac77f6a6841b64009bce.jpg", "2.3-2.3.7", getString(R.string.ginger_bread)))
        androidVersions.add(AndroidVersions("Android 3.0 Honeycomb", "https://i.pinimg.com/564x/1f/c3/2c/1fc32c434c350b20338f0087b8ad6475.jpg", "3.0-3.2.6", getString(R.string.honeycomb)))
        androidVersions.add(AndroidVersions("Android 4.0\n Ice Cream Sandwich", "https://3dnews.ru/assets/external/illustrations/2011/10/19/618587/ics-android.png", "4.0-4.0.4", getString(R.string.ice_cream)))
        androidVersions.add(AndroidVersions("Android 4.1 Jelly Bean", "https://i.pinimg.com/564x/cf/3a/aa/cf3aaa861e38899277d198bb97e99619.jpg", "4.1-4.3.1", getString(R.string.jelly_bean)))
        androidVersions.add(AndroidVersions("Android 4.4 KitKat", "https://i.pinimg.com/564x/a2/1e/b9/a21eb9b8a277571a8c035ed590afc5af.jpg", "4.4-4.4.4", getString(R.string.kitkat)))
        androidVersions.add(AndroidVersions("Android 5.0 Lollipop", "https://suvitruf.ru/wp-content/uploads/2014/10/android-lollipop.png", "5.0-5.1.1", getString(R.string.lollipop)))
        androidVersions.add(AndroidVersions("Android 6.0 Marshmallow", "https://i.pinimg.com/564x/aa/54/76/aa5476df92de946419ddbd72a3970f85.jpg", "6.0-6.0.1", getString(R.string.marshmallow)))
        androidVersions.add(AndroidVersions("Android 7.0 Nougat", "https://i.pinimg.com/564x/fa/55/68/fa5568f06f33984da3bfc2a755e24ce1.jpg", "7.0-7.1.2", getString(R.string.nougat)))
        androidVersions.add(AndroidVersions("Android 8.0 Oreo", "https://i.pinimg.com/564x/b8/42/57/b84257cf6db9ffe3f40661a2a2a9a6b6.jpg", "8.0-8.1", getString(R.string.oreo)))
        androidVersions.add(AndroidVersions("Android 9.0 Pie", "https://i.pinimg.com/564x/7b/df/a2/7bdfa22b717cd6033621d282e0e83d16.jpg", "9", getString(R.string.pie)))
        androidVersions.add(AndroidVersions("Android 10", "https://i.pinimg.com/564x/fe/95/6b/fe956b441971225a99cc5aecbd71444c.jpg", "10", getString(R.string.an_10)))
        androidVersions.add(AndroidVersions("Android 11", "https://i.pinimg.com/564x/c9/0f/47/c90f479eed92f188f1e2e6ace6631d29.jpg", "11", getString(R.string.an_11)))
        return androidVersions
    }
}