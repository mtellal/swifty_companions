package com.example.swifty_companion.models

data class Skill(
    val id: Int,
    val name: String,
    val level: Float
)
data class Cursus(
    val name: String,
)
data class UserCursus(
    val grade: String,
    val level: Float,
    val skills: Array<Skill>,
    val cursus: Cursus
)

data class Project(
    val name: String
)
data class ProjectData(
    val id: Int,
    val final_mark: Int?,
    val status: String,
    val project: Project,
    val cursus_ids: Array<Int>
)

data class UserDataModel(
    val id: Int,
    val login: String,
    val first_name: String,
    val last_name: String,
    val image: UserImage,
    val correction_point: Int,
    val wallet: Int,
    val cursus_users: Array<UserCursus>,
    val projects_users: Array<ProjectData>
)