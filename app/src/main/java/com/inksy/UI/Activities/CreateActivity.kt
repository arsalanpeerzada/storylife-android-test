package com.inksy.UI.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.inksy.R
import com.inksy.UI.Fragments.CreateJournalBackgroundBorderColorDirections
import com.inksy.UI.Fragments.CreateJournalCoverInfoDirections
import com.inksy.UI.Fragments.CreateJournalIndexDirections
import com.inksy.UI.Fragments.create_journal_entryDirections
import com.inksy.databinding.ActivityCreateBinding

class CreateActivity : AppCompatActivity() {

    lateinit var binding: ActivityCreateBinding
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.findFragmentById(binding.fragmentContainerView.id)?.let {
            navController = NavHostFragment.findNavController(it)

        }
    }

    override fun onBackPressed() {
        val currentid = Navigation.findNavController(this, R.id.fragmentContainerView)
            .currentDestination?.id

        when (currentid) {
            R.id.CreateJournalCoverInfo -> {
                // handle back button the way you want here
                val action =
                    CreateJournalCoverInfoDirections.actionCreateJournalCoverInfoToCreateJournalBackgroundBorderColor()
                navController.navigate(action)
                return;
            }
            R.id.createJournalIndex -> {
                // handle back button the way you want here
                val action =
                    CreateJournalIndexDirections.actionCreateJournalIndexToCreateJournalCoverInfo()
                navController.navigate(action)
                return;
            }
            R.id.create_journal_entry -> {
                val action =
                    create_journal_entryDirections.actionCreateJournalEntryToCreateJournalIndex()
                navController.navigate(action)
            }
            R.id.createJournalBackgroundBorderColor -> {
                val action =
                    CreateJournalBackgroundBorderColorDirections.actionCreateJournalBackgroundBorderColorToCreatejournal()
                navController.navigate(action)
            }
            else -> {
                this.finish()
            }
        }
    }
}