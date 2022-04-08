package com.example.reactiveprogramming.ui.dataManagement

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entity.Team
import com.example.reactiveprogramming.R
import com.google.android.material.textview.MaterialTextView

class DataManagementTeamAdapter(
    private val teamList: MutableList<Team>
) : RecyclerView.Adapter<DataManagementTeamAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.data_management_sport_view, p0, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return teamList.size
    }

    override fun onBindViewHolder(item: ViewHolder, position: Int) {
        item.teamNameLabel.text = teamList[position].name
        item.teamSportNameLabel.text = teamList[position].sport.name
        item.teamPlayersNumberLabel.text = "${teamList[position].players.size} Jugadores"
    }

    fun onNewData(newTeamList: List<Team>) {
        val diffResult = DiffUtil.calculateDiff(DataManagementDiffUtilCallback(newTeamList, teamList))
        diffResult.dispatchUpdatesTo(this)
        teamList.clear()
        teamList.addAll(newTeamList)
    }

    fun getTeamList() = teamList

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var teamNameLabel: MaterialTextView = itemView.findViewById(R.id.team_name_label)
        var teamSportNameLabel: MaterialTextView = itemView.findViewById(R.id.team_sport_name_label)
        var teamPlayersNumberLabel: MaterialTextView = itemView.findViewById(R.id.team_players_number)
    }
}
