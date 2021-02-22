package com.example.piano

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.piano.databinding.FragmentPianoBinding
import kotlinx.android.synthetic.main.fragment_piano.view.*

class PianoLayout : Fragment() {

    private var _binding: FragmentPianoBinding? = null
    private val binding get() = _binding!!

    private val fullNoter = listOf("C","D","E","F","G","A","B","C2","D2","E2","F2","G2")

    private val halvNoter = listOf("C#","D#","E#","F#","G#","A#","B#")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPianoBinding.inflate(layoutInflater)
        val view = binding.root
        val fm = childFragmentManager
        val ft = fm.beginTransaction()

        fullNoter.forEach {
            val fullNoterPianoKey = FullNoterPianoKeyFrag.newInstance(it)

            fullNoterPianoKey.onKeyDown = {
                println("Piano knapp er nede $it")
            }

            fullNoterPianoKey.onKeyUp = {
                println("Piano key op $it")
            }

            ft.add(view.fullNoteKeyLayout.id, fullNoterPianoKey, "note_$it")
        }

        halvNoter.forEach{
            val halvNoterPianoKey = HalvNoterPianoKeyFrag.newInstance(it)

            halvNoterPianoKey.onKeyDown = {
                println("Piano knapp er nede $it")
            }
            halvNoterPianoKey.onKeyUp = {
                println("Piano knapp op $it")
            }

            ft.add(view.halvNoteKeyLayout.id, halvNoterPianoKey, "note_$it")
        }

        ft.commit()

        return view
    }


}