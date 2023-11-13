package com.example.landlord.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.landlord.R
import com.example.landlord.adapters.UsersAdapter
import com.example.landlord.models.User
import com.example.landlord.repositories.AdminRepository
import com.example.landlord.utils.Utils
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AdminUserFragment : Fragment(), UsersAdapter.OnUserClickListener {

    // Declare variables
    private lateinit var usersRecyclerView: RecyclerView
    private lateinit var addUserButton: FloatingActionButton
    private lateinit var usersAdapter: UsersAdapter

    private var usersList = ArrayList<User>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_admin_user, container, false)

        // Bind the variables to the views in the layout
        usersRecyclerView = view.findViewById(R.id.usersRecyclerView)
        addUserButton = view.findViewById(R.id.addUserButton)

        // Get the users from the database
        if (Utils.isLoggedIn(requireActivity())) {
            val user = Utils.getUser(requireActivity())
            AdminRepository(requireContext()).getUsers(user) { users ->
                usersList = users
                usersRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                usersAdapter = UsersAdapter(usersList, this)
                usersRecyclerView.adapter = usersAdapter
            }
        }
        // Set the click listener for the add user button
        addUserButton.setOnClickListener {
            Toast.makeText(requireContext(), "Add User", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    override fun onUserClicked(position: Int) {
        Toast.makeText(requireContext(), "User Clicked", Toast.LENGTH_SHORT).show()
    }

    override fun onEditUserClicked(position: Int) {
        Utils.editUser(requireActivity(), usersList[position])
    }

    override fun onDeleteUserClicked(position: Int) {
        Utils.deleteUser(requireActivity(), usersList[position])
    }
}
