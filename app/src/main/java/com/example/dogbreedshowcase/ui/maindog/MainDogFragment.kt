package com.example.dogbreedshowcase.ui.maindog

import android.content.ContentValues.TAG
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dogbreedshowcase.listadapters.DogBreedAdapter
import com.example.dogbreedshowcase.api.recyclerViewLineDivider
import com.example.dogbreedshowcase.databinding.MainDogFragmentBinding

import com.example.dogbreedshowcase.model.DogBreeds

class MainDogFragment : Fragment() {
    private lateinit var viewModel: MainDogViewModel
    private lateinit var dogBreedAdapter: DogBreedAdapter

    private var _binding: MainDogFragmentBinding? = null
    private val binding get() = _binding!!


    private val dogBreeds = mutableListOf<DogBreeds>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = MainDogFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainDogViewModel::class.java)

        viewModel.breeds.observe(viewLifecycleOwner, { breeds ->
            val numElements = dogBreeds.size
            dogBreeds.clear()
            dogBreeds.addAll(breeds)
            dogBreedAdapter.notifyDataSetChanged()
            //binding.dogsRecyclerview.smoothScrollToPosition(numElements)
        })
        viewModel.isLoading.observe(viewLifecycleOwner, { isLoading ->
            Log.i(TAG, "isLoading $isLoading")
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })
        viewModel.errorMessage.observe(viewLifecycleOwner, { errorMessage ->
            if (errorMessage == null) {
                binding.displayError.visibility = View.GONE
            } else {
                binding.displayError.visibility = View.VISIBLE
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            }
        })

        dogBreedAdapter = DogBreedAdapter(requireContext(), dogBreeds, object : DogBreedAdapter.ItemClickListener{
            override fun onItemClick(dogBreeds: DogBreeds) {
                val action = MainDogFragmentDirections.actionDogImagesFromList(dogBreeds.breed)
                findNavController().navigate(action)
            }
        })


        context?.let {
            binding.dogsRecyclerview.recyclerViewLineDivider(it)
        }

        binding.dogsRecyclerview.adapter = dogBreedAdapter
        binding.dogsRecyclerview.layoutManager = LinearLayoutManager(requireContext())

        viewModel.getDogs()

    }
}