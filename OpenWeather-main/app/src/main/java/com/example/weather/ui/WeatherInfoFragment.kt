import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.weather.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class WeatherInfoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.forecast_list_item, container, false)

        // Button click listener
        val btnExpandMenu = view.findViewById<Button>(R.id.btn_expand_menu)
        btnExpandMenu.setOnClickListener {
            // Show the bottom sheet dialog fragment with weather info and recommendations
            val bottomSheetDialogFragment = WeatherInfoBottomSheetFragment()
            bottomSheetDialogFragment.show(parentFragmentManager, bottomSheetDialogFragment.tag)
        }

        return view
    }
}
