package com.example.piano

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.piano.data.Note
import com.example.piano.databinding.FragmentPianoBinding
import kotlinx.android.synthetic.main.fragment_piano.view.*
import java.io.File
import java.io.FileOutputStream

class PianoLayout : Fragment() {

    private var _binding: FragmentPianoBinding? = null
    private val binding get() = _binding!!

    private val fullNoter = listOf("C","D","E","F","G","A","B","C2","D2","E2","F2","G2")

    private val halvNoter = listOf("C#","D#","E#","F#","G#","A#","B#")
    private var noteListe:MutableList<Note> = mutableListOf<Note>()

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
            var startPlay:Long = 0

            fullNoterPianoKey.onKeyDown = { note ->
                startPlay = System.nanoTime()
                println("Piano knapp er nede $note")
            }

            fullNoterPianoKey.onKeyUp = {
                var endPlay = System.nanoTime()
                var fullTonerTotTid: Double = 0.0
                var fullTonerTid: Long = 0
                fullTonerTid = endPlay - startPlay
                fullTonerTotTid = fullTonerTid.toDouble() / 1000000000

                val note = Note(it, startPlay, fullTonerTotTid)
                noteListe.add(note)
                println("Piano key op $note")
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


        view.saveScoreBt.setOnClickListener {
            var fileNavn = view.filNavnTextEdit.text.toString()
            val path = this.activity?.getExternalFilesDir(null)
            val nyNoteFil = (File(path, fileNavn))

            when {
                noteListe.count() == 0 -> Toast.makeText(activity, "Forgot notes? Did you click by mistake?", Toast.LENGTH_SHORT).show()
                fileNavn.isEmpty() -> Toast.makeText(activity, "Forgot filename?", Toast.LENGTH_SHORT).show()
                path == null -> Toast.makeText(activity, "Are you sure this is the right path?", Toast.LENGTH_SHORT).show()
                nyNoteFil.exists() -> Toast.makeText(activity, "You already did this one!", Toast.LENGTH_SHORT).show()


                else -> {
                    fileNavn = "$fileNavn.txt"
                    FileOutputStream(nyNoteFil, true).bufferedWriter().use { writer ->
                        noteListe.forEach {
                            writer.write("${it.toString()}\n")
                        }
                        FileOutputStream(nyNoteFil).close()
                    }

                    Toast.makeText(activity, "Great job, Beethoven! Your file was successful.", Toast.LENGTH_SHORT).show()
                    noteListe.clear()




                    println("File saves as $fileNavn at $path/$fileNavn")
                }

            }
        }

        return view
    }


}