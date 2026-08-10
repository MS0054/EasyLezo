package am.mojtaba.armengo.ui.ads

import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import kotlin.random.Random

class InterstitialAdManager(private val context: Context) {
    private var interstitialAd: InterstitialAd? = null

    // Ad Unit ID تست گوگل برای Interstitial
    // موقع ریلیز نهایی حتماً Ad Unit ID واقعی خودت رو بزار
    private val adUnitId = "ca-app-pub-1194167844442800/1949870864"

    init {
        loadAd()
    }

    fun loadAd() {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            context,
            adUnitId,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitialAd = ad
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    interstitialAd = null
                }
            }
        )
    }

    /**
     * با احتمال مشخص شده تبلیغ را نشان می‌دهد
     * @param probability درصد احتمال نمایش (مثلا 0.30 یعنی ۳۰ درصد)
     */
    fun showAdWithProbability(activity: Activity, probability: Float = 0.30f, onAdDismissed: () -> Unit) {
        val randomValue = Random.nextFloat() // عددی بین 0.0 تا 1.0 تولید می‌کند

        // اگر شانس کمتر از ۳۰٪ بود و تبلیغ هم لود شده بود
        if (randomValue < probability && interstitialAd != null) {
            interstitialAd?.show(activity)
            interstitialAd = null
            loadAd() // بارگذاری مجدد برای دفعه بعدی
            onAdDismissed()
        } else {
            // اگر شانس نیاورد یا تبلیغ هنوز لود نشده بود، مستقیم کاربر می‌رود صفحه بعدی
            onAdDismissed()
            if (interstitialAd == null) loadAd()
        }
    }
}