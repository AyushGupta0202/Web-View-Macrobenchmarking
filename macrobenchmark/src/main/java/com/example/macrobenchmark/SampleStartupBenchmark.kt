import android.os.Build
import androidx.annotation.RequiresApi
import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.ExperimentalMacrobenchmarkApi
import androidx.benchmark.macro.ExperimentalMetricApi
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.StartupTimingMetric
import androidx.benchmark.macro.TraceSectionMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalMacrobenchmarkApi::class, ExperimentalMetricApi::class)
@LargeTest
@RunWith(AndroidJUnit4::class)
class SampleStartupBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

//    @RequiresApi(Build.VERSION_CODES.Q)
    @Test
    fun startup() = benchmarkRule.measureRepeated(
        packageName = "com.example.webviewpoc",
        metrics = listOf(
            StartupTimingMetric(),
            TraceSectionMetric("onCreate"),
            TraceSectionMetric("addWebView"),
            TraceSectionMetric("webViewInit"),
            TraceSectionMetric("loadUrl"),
        ),
        iterations = 20,
        setupBlock = {
            killProcess()
        },
        startupMode = StartupMode.COLD,
        compilationMode = CompilationMode.Ignore()
    ) {
        // starts default launch activity
//        pressHome()
        startActivityAndWait()
    }
}