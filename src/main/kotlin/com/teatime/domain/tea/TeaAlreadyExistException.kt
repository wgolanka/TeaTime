package com.teatime.domain.tea

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Tea with this name already exist")
class TeaAlreadyExistException : Exception()