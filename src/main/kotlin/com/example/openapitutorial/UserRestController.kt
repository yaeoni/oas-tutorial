package com.example.openapitutorial

import com.contracts.user.User
import com.contracts.user.UserApi
import com.contracts.user.UserApiDelegate
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class UserRestController(
) : UserApi {
    override fun getDelegate(): UserApiDelegate {
        return UserApiDelegateImpl()
    }

    /**
     * swagger ui path : http://localhost:8080/swagger-ui/index.html
     */
    private class UserApiDelegateImpl : UserApiDelegate {
        /**
         * GET /api/v1/user/{id}
         */
        override fun apiV1UserIdGet(id: Long): ResponseEntity<User> {
            return ResponseEntity.ok(
                User(
                    id = 1,
                    nickname = "Heidi",
                    gender = User.Gender.female
                )
            )
        }
    }

}