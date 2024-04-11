package com.yasinmaden.improveyourposturein30days.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.yasinmaden.improveyourposturein30days.R
import com.yasinmaden.improveyourposturein30days.model.Exercise
import com.yasinmaden.improveyourposturein30days.ui.theme.ImproveYourPostureIn30DaysTheme
import com.yasinmaden.improveyourposturein30days.ui.theme.Shapes


@Composable
fun YoutubePlayer(
    youtubeVideoId: String,
    lifecycleOwner: LifecycleOwner,
    modifier: Modifier = Modifier
) {
    AndroidView(modifier = modifier
        .fillMaxWidth()
        .padding(8.dp)
        .clip(Shapes.small),
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
    modifier: Modifier = Modifier){
    Card {
        Column {
            Text(text = stringResource(exercise.nameRes))
            YoutubePlayer(youtubeVideoId = stringResource(exercise.videoUrlRes), lifecycleOwner = LocalLifecycleOwner.current)
            Text(text = stringResource(id = exercise.descriptionRes))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExerciseCardPreview(){
    ImproveYourPostureIn30DaysTheme{
        val exercise1 = Exercise(
            nameRes = R.string.day1_title,
            videoUrlRes = R.string.day1_videoURL,
            descriptionRes = R.string.day1_description
        )
        ExerciseCard(exercise1)
    }

}
@Preview(showBackground = true)
@Composable
fun AppPreview(){
    ImproveYourPostureIn30DaysTheme{

    }
}