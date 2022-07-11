package br.com.joaov.network.domain.exception

import java.lang.Exception

class ServerException(
    errorMessage: String? = ""
): Exception(errorMessage)