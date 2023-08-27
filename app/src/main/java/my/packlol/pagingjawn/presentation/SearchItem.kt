package my.packlol.pagingjawn.presentation

import android.graphics.Color
import android.graphics.fonts.FontStyle
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import my.packlol.pagingjawn.domain.SavableBeer
import my.packlol.pagingjawn.navigation.Screen

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun SearchItem(
    onSaveClick : (Int) -> Unit,
    beer : SavableBeer,
    modifier : Modifier = Modifier,
    nav : (Screen) -> Unit
    ) {
        val context = LocalContext.current
        Button(onClick = {nav(Screen.beerScreen)}) {
            
        }
        Card(
            modifier = Modifier,
            elevation = 4.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max)
                    .padding(16.dp)
            ) {
                AsyncImage(

                    model = ImageRequest.Builder(context).data(beer.imageUrl).crossfade(true).build(),
                    contentDescription = beer.name,
                    modifier = Modifier
                        .weight(1f)
                        .height(150.dp))

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier
                        .weight(3f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            onSaveClick(beer.id)

                        }){
                    }

                    Text(
                        text = beer.name,
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.fillMaxWidth())

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = beer.tagline,
                        modifier = Modifier.fillMaxWidth())

                    Text(
                        text = beer.description,
                        modifier = Modifier.fillMaxWidth())

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "First Brewed In:" + beer.firstBrewed,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End,
                        fontSize = 8.sp)

                }
            }

        }
}