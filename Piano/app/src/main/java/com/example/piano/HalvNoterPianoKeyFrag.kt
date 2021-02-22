package com.example.piano

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.example.piano.databinding.FragmentHalvNoterPianoKeyBinding
import kotlinx.android.synthetic.main.fragment_full_toner_piano_key.view.*
import kotlinx.android.synthetic.main.fragment_halv_noter_piano_key.view.*

class HalvNoterPianoKeyFrag : Fragment() {

    private var _binding: FragmentHalvNoterPianoKeyBinding? = null
    private val binding get() = _binding!!
    private lateinit var note:String

    var onKeyDown:((note:String) -> Unit)? = null
    var onKeyUp:((note:String) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            note = it.getString("NOTE") ?: "?"
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHalvNoterPianoKeyBinding.inflate(inflater)
        val view = binding.root

        view.halvNoteKey.setOnTouchListener(object: View.OnTouchListener{
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when(event?.action){
                    MotionEvent.ACTION_DOWN -> this@HalvNoterPianoKeyFrag.onKeyDown?.invoke(note)
                    MotionEvent.ACTION_UP -> this@HalvNoterPianoKeyFrag.onKeyUp?.invoke(note)
                }
                return true
            }
        })
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(note: String) =
            HalvNoterPianoKeyFrag().apply {
                arguments = Bundle().apply {
                    putString("NOTE", note)
                }
            }
    }
}