package com.yasinmaden.improveyourposturein30days.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.yasinmaden.improveyourposturein30days.R
import com.yasinmaden.improveyourposturein30days.data.DataSource
import com.yasinmaden.improveyourposturein30days.model.Exercise
import com.yasinmaden.improveyourposturein30days.ui.theme.ImproveYourPostureIn30DaysTheme
import com.yasinmaden.improveyourposturein30days.ui.theme.Shapes

// TODO: autoplay will be stopped.
@Composable
fun YoutubePlayer(
    youtubeVideoId: String,
    lifecycleOwner: LifecycleOwner,
    modifier: Modifier = Modifier
) {
    AndroidView(modifier = modifier
        .fillMaxWidth()
        .padding(dimensionResource(id = R.dimen.small))
        .clip(Shapes.medium),
        factory = { context ->
            YouTubePlayerView(context = context).apply {
                lifecycleOwner.lifecycle.addObserver(this)

                addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.loadVideo(youtubeVideoId, 0f)
                    }
                })
            }
        })
}

@Composable
fun ExerciseCard(
    exercise: Exercise,
    modifier: Modifier = Modifier
) {
    Card(
        shape = Shapes.large,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ), modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.medium), vertical = dimensionResource(id = R.dimen.small))
    ) {
        Column(modifier = Modifier.padding(dimensionResource(id = R.dimen.medium))) {
            Text(
                text = stringResource(exercise.nameRes),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.small))
            )
            YoutubePlayer(
                youtubeVideoId = stringResource(exercise.videoUrlRes),
                lifecycleOwner = LocalLifecycleOwner.current
            )
            Text(
                text = stringResource(id = exercise.descriptionRes),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.small))
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseApp(modifier: Modifier = Modifier) {
    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = {
            Text(
                text = "Improve Your Posture",
                style = MaterialTheme.typography.titleLarge
            )
        })
    }) { paddingValues ->
        LazyColumn(modifier = modifier.padding(paddingValues)) {
            items(DataSource.exercises) { exercise ->
                ExerciseCard(exercise = exercise)
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun ExerciseCardPreview() {
    ImproveYourPostureIn30DaysTheme {
//        val exercise1 = Exercise(
//            nameRes = R.string.day1_title,
//            videoUrlRes = R.string.day1_videoURL,
//            descriptionRes = R.string.day1_description
//        )
//        ExerciseCard(exercise1)


    }

}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    ImproveYourPostureIn30DaysTheme {
        ExerciseApp()
    }
}