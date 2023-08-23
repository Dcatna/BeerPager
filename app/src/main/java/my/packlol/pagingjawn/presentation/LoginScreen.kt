package my.packlol.pagingjawn.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import my.packlol.pagingjawn.navigation.Screen

@Composable
fun LoginScreen(viewModel : BeerVM, nav : (Screen) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
        )

    {
        ClickableText(
            text = AnnotatedString("Sign Up Here!"), 
            onClick = {},
            modifier = Modifier.align(Alignment.BottomCenter))
    }
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
    
    ) {
        val username = remember { mutableStateOf(TextFieldValue())}
        val password = remember { mutableStateOf(TextFieldValue())}
        
        Text(text = "Login!")
        Spacer(modifier = Modifier.height(20.dp))
        
        TextField(
            value = username.value, 
            onValueChange = {username.value = it},
            label = { Text(text = "USERNAME")}
        )
        
        Spacer(modifier = Modifier.height(20.dp))
        
        TextField(
            value = password.value, 
            onValueChange = {password.value = it},
            visualTransformation = PasswordVisualTransformation(),
            label = { Text(text = "PASSWORD")}
        )
        Spacer(modifier = Modifier.height(20.dp))
        
        Box(modifier = Modifier.padding(start=40.dp, end = 40.dp )){
            Button(
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                onClick = { nav(Screen.beerScreen) }
            ) {
                Text(text = "LOGIN")
            }
        }
            
    }
}
fun Modifier.noRippleClickable(action : ()-> Unit) : Modifier{
    return this.composed {
        clickable(
            interactionSource = remember{
                MutableInteractionSource()
            },
            indication = null
        ) {
            action()
        }
    }

}