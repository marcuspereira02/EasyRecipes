package com.devspace.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.devspace.myapplication.ui.theme.EasyRecipesTheme

@Composable
fun OnboardingScreen(navController: NavHostController) {
    OnboardingContent(){
        navController.navigate("mainScreen")
    }
}

@Composable
private fun OnboardingContent(onClick : () -> Unit) {

    Image(
        painter = painterResource(id = R.drawable.new_onboarding),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.8f))
    )

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {

        Text(
            modifier = Modifier.padding(8.dp),
            text = "   Let's\n\nCooking",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Text(
            modifier = Modifier.padding(8.dp),
            text = "Find best recipes for cooking",
            fontSize = 16.sp,
            color = Color.White
        )

        Button(
            onClick = onClick,
            modifier = Modifier.padding(top = 8.dp, bottom = 48.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red,
                contentColor = Color.White
            )
        ) {
            Text(
                fontWeight = FontWeight.Bold,
                text = "Start Cooking",
                fontSize = 16.sp,

            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun OnboardingPreview() {
    EasyRecipesTheme {

        OnboardingContent(onClick ={})
    }

}