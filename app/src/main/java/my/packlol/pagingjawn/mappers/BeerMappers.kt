package my.packlol.pagingjawn.mappers

import my.packlol.pagingjawn.data.remote.BeerDto
import my.packlol.pagingjawn.domain.Beer
import my.packlol.pagingjawn.domain.SavableBeer
import my.packlol.pagingjawn.local.BeerEntity
import my.packlol.pagingjawn.local.Favs

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

fun BeerEntity.toSavableBeer(saved : Boolean) : SavableBeer {
    return SavableBeer(
        id = id,
        name = name,
        tagline = tagline,
        description = description,
        firstBrewed = firstBrewed,
        imageUrl = imageUrl,
        saved = saved
    )
}

fun Favs.toSavableBeer() : SavableBeer{
    return SavableBeer(id = id,
        name = name,
        tagline = tagline,
        description = description,
        firstBrewed = firstBrewed,
        imageUrl = imageUrl,
        saved = false
    )
}

fun BeerEntity.toFav(saved : Boolean) : Favs{
    return Favs(
        id = id,
        name = name,
        tagline = tagline,
        description = description,
        firstBrewed = firstBrewed,
        imageUrl = imageUrl,
        saved = saved
    )
}