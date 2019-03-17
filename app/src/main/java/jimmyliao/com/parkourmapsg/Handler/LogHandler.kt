package jimmyliao.com.parkourmapsg.Handler

import android.util.Log

class LogHandler {

    companion object {
        private const val debugMode = false

        fun Debug(tag: String, msg: String) {
            if (debugMode) {
                Log.d(tag, msg)
            }
        }

        fun Info(tag: String, msg: String) {
            Log.i(tag, msg)
        }

        fun Warn(tag: String, msg: String) {
            Log.w(tag, msg)
        }

        fun Error(tag: String, msg: String) {
            Log.e(tag, msg)
        }


    }
}