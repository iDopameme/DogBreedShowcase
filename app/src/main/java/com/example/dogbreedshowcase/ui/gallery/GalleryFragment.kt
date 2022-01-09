package com.example.dogbreedshowcase.ui.gallery

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.view.ActionMode
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dogbreedshowcase.R
import com.example.dogbreedshowcase.databinding.GalleryFragmentBinding
import com.example.dogbreedshowcase.listadapters.DogImageAdapter
import com.example.dogbreedshowcase.model.DogImage

class GalleryFragment : Fragment(), ActionMode.Callback {
    private lateinit var viewModel: GalleryViewModel
    private lateinit var dogImageAdapter: DogImageAdapter
//  private lateinit var tracker: SelectionTracker

    private var actionMode: ActionMode? = null

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


        return binding.root
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
                viewModel.displayPopupOptions(dogImage)
            }
        })

        binding.dogImageRecyclerview.adapter = dogImageAdapter

        binding.dogImageRecyclerview.layoutManager = GridLayoutManager(requireContext(), 2)

        dogString?.let { viewModel.getImages(it) }
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mode?.menuInflater?.inflate(R.menu.contextual_action_bar, menu)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return false
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        return when (item!!.itemId) {
            R.id.favorite -> {
                //TODO Add favorite identifier to selected image
                true
            }
            R.id.share -> {
                //TODO Share the selected image via email
                true
            }
            else -> false
        }
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        actionMode = null
    }


}