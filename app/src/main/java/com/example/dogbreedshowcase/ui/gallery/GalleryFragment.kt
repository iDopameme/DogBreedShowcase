package com.example.dogbreedshowcase.ui.gallery

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.view.ActionMode
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dogbreedshowcase.R
import com.example.dogbreedshowcase.databinding.GalleryFragmentBinding
import com.example.dogbreedshowcase.listadapters.DogImageAdapter
import com.example.dogbreedshowcase.model.DogImage

class GalleryFragment : Fragment() {
    private lateinit var viewModel: GalleryViewModel
    private lateinit var dogImageAdapter: DogImageAdapter

    private var _binding: GalleryFragmentBinding? = null
    private val binding get() = _binding!!

    private var dogString: String? = null


    private val dogImages = mutableListOf<DogImage>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = GalleryFragmentBinding.inflate(inflater)

        registerForContextMenu(binding.dogImageRecyclerview)

        return binding.root
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?,
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = MenuInflater(requireContext())
        inflater.inflate(R.menu.header_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.share -> {
                true
            }
            R.id.favorite -> {
                true
            }
            else -> {super.onContextItemSelected(item)}
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val safeArgs = GalleryFragmentArgs.fromBundle(it)
            dogString = safeArgs.dogString
        }

        viewModel = ViewModelProvider(this).get(GalleryViewModel::class.java)

        viewModel.images.observe(viewLifecycleOwner, { images ->
            val numElements = dogImages.size
            dogImages.clear()
            dogImages.addAll(images)
            dogImageAdapter.notifyDataSetChanged()
            binding.dogImageRecyclerview.smoothScrollToPosition(numElements)
        })
        viewModel.isLoading.observe(viewLifecycleOwner, { isLoading ->
            Log.i(TAG, "isLoading $isLoading")
            binding.progressBarImage.visibility = if (isLoading) View.VISIBLE else View.GONE
        })
        viewModel.errorMessage.observe(viewLifecycleOwner, { errorMessage ->
            if (errorMessage == null) {
                binding.imageDisplayError.visibility = View.GONE
            } else {
                binding.imageDisplayError.visibility = View.VISIBLE
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            }
        })

        dogImageAdapter = DogImageAdapter(requireContext(), dogImages, object : DogImageAdapter.OnClickListener {
            override fun onImageClick(dogImage: DogImage) {
                TODO("Not yet implemented. Need to pass dogImage parameter to context options")
            }
        })

        binding.dogImageRecyclerview.adapter = dogImageAdapter

        binding.dogImageRecyclerview.layoutManager = GridLayoutManager(requireContext(), 2)

        dogString?.let { viewModel.getImages(it) }
    }
}