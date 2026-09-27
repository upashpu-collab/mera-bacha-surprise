import React, { useRef } from 'react';
import { ArrowRight, Disc, FolderOpen, Heart, Music, Pause, Play, RefreshCw, Volume2 } from 'lucide-react';
import { BIRTHDAY_CONFIG } from '../config';
import { AudioPlayerState } from '../utils/audio';

interface MusicSectionProps {
  playerState: AudioPlayerState;
  onTogglePlay: () => void;
  onCustomAudioFile: (url: string) => void;
  onResetAudio: () => void;
  onContinue: () => void;
}

export const MusicSection: React.FC<MusicSectionProps> = ({
  playerState,
  onTogglePlay,
  onCustomAudioFile,
  onResetAudio,
  onContinue,
}) => {
  const fileInputRef = useRef<HTMLInputElement>(null);

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (file) {
      const objectUrl = URL.createObjectURL(file);
      onCustomAudioFile(objectUrl);
    }
    e.target.value = '';
  };

  return (
    <div className="relative min-h-[85vh] py-8 px-4 max-w-md mx-auto z-10 flex flex-col items-center">
      {/* Hidden MP3 File Picker */}
      <input
        type="file"
        ref={fileInputRef}
        onChange={handleFileChange}
        accept="audio/*"
        className="hidden"
      />

      {/* Header */}
      <div className="text-center mb-6">
        <div className="inline-flex items-center gap-1.5 px-3 py-1 rounded-full bg-rose-50 text-rose-600 text-xs font-semibold tracking-wider uppercase mb-2">
          <Music className="w-3.5 h-3.5 text-rose-500" />
          <span>Our Soundtrack</span>
          <Music className="w-3.5 h-3.5 text-rose-500" />
        </div>

        <h2 className="font-serif-romantic text-2xl sm:text-3xl font-bold text-rose-950 mb-1">
          A Romantic Melody 🎶
        </h2>

        <p className="text-rose-900/70 text-sm italic">
          Press Play to fill this moment with our melody
        </p>
      </div>

      {/* Turntable / Vinyl Player Card */}
      <div className="w-full bg-white/95 backdrop-blur-md rounded-3xl p-6 shadow-2xl border-2 border-rose-100 flex flex-col items-center mb-8">
        {/* Vinyl Record */}
        <div className="relative my-4">
          {/* Subtle outer glow */}
          <div className={`absolute -inset-4 rounded-full blur-xl transition duration-700 ${playerState.isPlaying ? 'bg-rose-400/30' : 'bg-transparent'}`} />

          <div
            className={`relative w-48 h-48 sm:w-56 sm:h-56 rounded-full bg-gradient-to-tr from-neutral-900 via-neutral-800 to-neutral-900 shadow-2xl flex items-center justify-center border-4 border-neutral-700/50 ${
              playerState.isPlaying ? 'animate-spin-slow' : ''
            }`}
          >
            {/* Vinyl Grooves */}
            <div className="w-40 h-40 sm:w-44 sm:h-44 rounded-full border border-neutral-700/40 flex items-center justify-center">
              <div className="w-32 h-32 sm:w-36 sm:h-36 rounded-full border border-neutral-700/30 flex items-center justify-center">
                <div className="w-24 h-24 sm:w-28 sm:h-28 rounded-full border border-neutral-700/40 flex items-center justify-center">
                  {/* Center Record Label */}
                  <div className="w-16 h-16 sm:w-20 sm:h-20 rounded-full bg-gradient-to-tr from-rose-600 to-pink-400 p-1 shadow-inner flex items-center justify-center border-2 border-amber-300">
                    <Heart className="w-8 h-8 text-white fill-white animate-pulse" />
                  </div>
                </div>
              </div>
            </div>

            {/* Spindle hole */}
            <div className="absolute w-3 h-3 rounded-full bg-neutral-900 border border-neutral-600" />
          </div>
        </div>

        {/* Dancing Equalizer Waveform */}
        <div className="flex items-end justify-center gap-1.5 h-8 my-4">
          {[40, 75, 55, 90, 60, 100, 70, 85, 45, 95, 65, 80].map((h, i) => (
            <div
              key={i}
              className={`w-1.5 rounded-full transition-all duration-300 ${
                playerState.isPlaying
                  ? 'bg-rose-500 animate-pulse'
                  : 'bg-rose-200 h-2'
              }`}
              style={{
                height: playerState.isPlaying ? `${Math.max(20, (h * (i % 2 === 0 ? 0.9 : 1.1)))}%` : '8px',
                animationDelay: `${i * 120}ms`
              }}
            />
          ))}
        </div>

        {/* Track Info */}
        <div className="text-center mb-4">
          <h3 className="font-serif-romantic text-lg sm:text-xl font-bold text-rose-950">
            {playerState.isCustom ? "My Special Song for You ❤️" : BIRTHDAY_CONFIG.music.title}
          </h3>
          <p className="text-xs text-rose-800/70 italic mt-0.5">
            {playerState.isCustom ? "Custom audio file loaded" : BIRTHDAY_CONFIG.music.artist}
          </p>
        </div>

        {/* Progress Bar */}
        <div className="w-full bg-rose-100 rounded-full h-2 mb-6 overflow-hidden">
          <div
            className="bg-gradient-to-r from-rose-500 to-pink-500 h-full rounded-full transition-all duration-300"
            style={{ width: `${Math.min(100, Math.max(0, playerState.progress))}%` }}
          />
        </div>

        {/* Master Play / Pause Button */}
        <button
          onClick={onTogglePlay}
          className="w-16 h-16 rounded-full bg-gradient-to-tr from-rose-600 to-pink-500 text-white flex items-center justify-center shadow-xl shadow-rose-500/30 transform transition duration-300 hover:scale-110 active:scale-95 border-2 border-white"
        >
          {playerState.isPlaying ? (
            <Pause className="w-7 h-7 fill-white" />
          ) : (
            <Play className="w-7 h-7 fill-white ml-1" />
          )}
        </button>

        {/* Easy Music Replacement / Custom Upload */}
        <div className="mt-6 pt-4 border-t border-rose-100 w-full flex flex-col items-center gap-2">
          <div className="flex items-center gap-2">
            <button
              onClick={() => fileInputRef.current?.click()}
              className="px-3.5 py-1.5 rounded-full bg-rose-50 hover:bg-rose-100 text-rose-700 text-xs font-semibold flex items-center gap-1.5 border border-rose-200 transition"
            >
              <FolderOpen className="w-3.5 h-3.5 text-rose-600" />
              <span>Choose Your Song (.mp3) 📁</span>
            </button>

            {playerState.isCustom && (
              <button
                onClick={onResetAudio}
                title="Reset to romantic music box chime"
                className="p-1.5 rounded-full hover:bg-rose-50 text-rose-600 text-xs"
              >
                <RefreshCw className="w-4 h-4" />
              </button>
            )}
          </div>

          <p className="text-[11px] text-rose-400 font-light text-center">
            {playerState.isCustom
              ? "Your custom song is playing!"
              : "Built-in sweet romantic music box melody is ready!"}
          </p>
        </div>
      </div>

      {/* Continue Button */}
      <div className="text-center pb-6">
        <button
          onClick={onContinue}
          className="w-full sm:w-auto px-10 py-4 rounded-full bg-gradient-to-r from-rose-600 via-rose-500 to-pink-500 text-white font-semibold text-lg tracking-wide shadow-lg shadow-rose-500/30 transform transition duration-300 hover:scale-105 active:scale-95 inline-flex items-center justify-center gap-3 border border-white/30"
        >
          <span>One More Thing... ✨</span>
          <ArrowRight className="w-5 h-5" />
        </button>
      </div>
    </div>
  );
};
