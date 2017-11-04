package com.tdanylchuk.app.sound

import android.view.SoundEffectConstants
import android.view.View

class ClickSoundPlayer(private val view: View) : SoundPlayer {

    override fun play() {
        view.playSoundEffect(SoundEffectConstants.CLICK)
    }
}
