package com.budge.api.domain.auth

import com.budge.api.persistence.models.UserModel

class Session(val user : UserModel, val token : String) {
}