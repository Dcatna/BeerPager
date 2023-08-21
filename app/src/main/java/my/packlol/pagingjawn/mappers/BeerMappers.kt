package my.packlol.pagingjawn.mappers

import my.packlol.pagingjawn.data.remote.BeerDto
import my.packlol.pagingjawn.domain.Beer
import my.packlol.pagingjawn.local.BeerEntity

fun BeerDto.toBeerEntity() : BeerEntity{
    return BeerEntity(
        id = id,
        name = name,
        tagline = tagline,
        description = description,
        firstBrewed = first_brewed,
        imageUrl = image_url

    )
}

fun BeerEntity.toBeer() : Beer {
    return Beer(
        id = id,
        name = name,
        tagline = tagline,
        description = description,
        first_brewed = firstBrewed,
        image_url = imageUrl

    )
}