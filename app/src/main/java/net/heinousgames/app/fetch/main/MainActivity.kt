package net.heinousgames.app.fetch.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import net.heinousgames.app.fetch.R
import net.heinousgames.app.fetch.api.HiringObject
import net.heinousgames.app.fetch.ui.theme.FetchCodingExerciseTheme
import net.heinousgames.app.fetch.ui.theme.FetchPurple
import net.heinousgames.app.fetch.ui.theme.FetchYellow
import net.heinousgames.app.fetch.ui.theme.HeaderPurple
import net.heinousgames.app.fetch.ui.theme.lexendFamily
import net.heinousgames.app.fetch.ui.theme.syneFamily

@OptIn(ExperimentalFoundationApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = MainViewModel()

        enableEdgeToEdge()
        setContent {

            val hiringMap = viewModel.hiringMap.collectAsState()

            FetchCodingExerciseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    if (hiringMap.value.isNotEmpty()) {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Black)
                                .padding(innerPadding)
                        ) {
                            hiringMap.value.forEach { (listId, objectsForId) ->
                                stickyHeader {
                                    Text(
                                        text = "listId: $listId",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(HeaderPurple)
                                            .padding(8.dp),
                                        color = FetchYellow,
                                        fontFamily = lexendFamily,
                                    )
                                }

                                items(objectsForId) { hiringObj ->
                                    HiringObjectRow(hiringObj)
                                }
                            }
                        }
                    } else {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.width(64.dp),
                                color = FetchPurple,
                                trackColor = FetchYellow
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HiringObjectRow(hiringObj: HiringObject) {
    Column(modifier = Modifier.fillMaxWidth().background(color = FetchPurple).padding(16.dp)) {
        Text(getAnnotatedString(stringResource(R.string.listId), hiringObj.listId.toString()))
        Text(getAnnotatedString(stringResource(R.string.name), hiringObj.name!!))
        Text(getAnnotatedString(stringResource(R.string.id), hiringObj.id.toString()))
    }
}

private fun getAnnotatedString(strResource: String, arg: String): AnnotatedString {
    return buildAnnotatedString {
        withStyle(style = SpanStyle(color = FetchYellow, fontFamily = lexendFamily)) {
            append(strResource)
        }
        withStyle(style = SpanStyle(color = FetchYellow, fontFamily = syneFamily)) {
            append(arg)
        }
    }
}