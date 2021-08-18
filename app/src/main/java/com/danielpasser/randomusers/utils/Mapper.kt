package com.danielpasser.randomusers.utils

import com.danielpasser.randomusers.models.Dob
import com.danielpasser.randomusers.models.Name
import com.danielpasser.randomusers.models.Picture
import com.danielpasser.randomusers.models.User
import com.danielpasser.randomusers.room.UserDbEntity


class Mapper() {


    fun userDbEntityToUser(entity: UserDbEntity): User {
        val name = Name(entity.first, entity.last)
        val picture = Picture(entity.large, entity.medium, entity.thumbnail)
        val dob = Dob(entity.date)
        return User(name, entity.email, picture, dob)
    }

    fun userToUserDbEntity(user: User): UserDbEntity {

        return UserDbEntity(
            0,
            user.email,
            user.name?.first,
            user.name?.last,
            user.picture?.large,
            user.picture?.medium,
            user.picture?.thumbnail,
            user.dob?.date
        )
    }

    fun userDbEntityToUserList(entities: List<UserDbEntity>): List<User> {
        return entities.map { userDbEntityToUser(it) }
    }

    fun userToUserDbEntityList(users: List<User>): List<UserDbEntity> {
        return users.map { userToUserDbEntity(it) }
    }


}