package com.aydinpolat.bitcointicker.domain.use_case.main

import com.aydinpolat.bitcointicker.R
import com.aydinpolat.bitcointicker.domain.auth.UserProxy
import javax.inject.Inject

class GetAppStartDestinationUseCase @Inject constructor(
    private val userProxy: UserProxy
) {
    operator fun invoke(): Int {
        return if (userProxy.getUser() != null) {
            R.id.coinListFragment
        } else {
            R.id.loginFragment
        }
    }
}