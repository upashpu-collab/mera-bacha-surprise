/**
 * Romantic Audio Engine:
 * 1. Synthesizes a sweet, soothing acoustic music-box chime melody
 *    directly using Web Audio API (zero copyright, zero external files required).
 * 2. Seamlessly plays any custom MP3 file uploaded by the user or from /public.
 * 3. Never autoplays without user interaction.
 */

class RomanticAudioEngine {
  private audioCtx: AudioContext | null = null;
  private isPlaying: boolean = false;
  private synthTimeoutId: number | null = null;
  private currentNoteIndex: number = 0;
  private customAudio: HTMLAudioElement | null = null;
  private customAudioUrl: string = '';
  private progressInterval: number | null = null;
  private listeners: Set<(state: AudioPlayerState) => void> = new Set();

  private progress: number = 0;

  // Romantic music box chime notes (frequencies in Hz and durations in ms)
  // Happy Birthday / Sweet Love Ballad
  private melody = [
    { freq: 261.63, dur: 380 }, // C4
    { freq: 261.63, dur: 380 }, // C4
    { freq: 293.66, dur: 750 }, // D4
    { freq: 261.63, dur: 750 }, // C4
    { freq: 349.23, dur: 750 }, // F4
    { freq: 329.63, dur: 1300 },// E4

    { freq: 261.63, dur: 380 }, // C4
    { freq: 261.63, dur: 380 }, // C4
    { freq: 293.66, dur: 750 }, // D4
    { freq: 261.63, dur: 750 }, // C4
    { freq: 392.00, dur: 750 }, // G4
    { freq: 349.23, dur: 1300 },// F4

    { freq: 261.63, dur: 380 }, // C4
    { freq: 261.63, dur: 380 }, // C4
    { freq: 523.25, dur: 750 }, // C5
    { freq: 440.00, dur: 750 }, // A4
    { freq: 349.23, dur: 750 }, // F4
    { freq: 329.63, dur: 750 }, // E4
    { freq: 293.66, dur: 1100 },// D4

    { freq: 466.16, dur: 380 }, // Bb4
    { freq: 466.16, dur: 380 }, // Bb4
    { freq: 440.00, dur: 750 }, // A4
    { freq: 349.23, dur: 750 }, // F4
    { freq: 392.00, dur: 750 }, // G4
    { freq: 349.23, dur: 1600 } // F4
  ];

  public subscribe(listener: (state: AudioPlayerState) => void) {
    this.listeners.add(listener);
    listener(this.getState());
    return () => {
      this.listeners.delete(listener);
    };
  }

  private notify() {
    const state = this.getState();
    this.listeners.forEach(fn => fn(state));
  }

  public getState(): AudioPlayerState {
    return {
      isPlaying: this.isPlaying,
      progress: this.progress,
      isCustom: Boolean(this.customAudioUrl)
    };
  }

  public togglePlay() {
    if (this.isPlaying) {
      this.pause();
    } else {
      this.play();
    }
  }

  public play() {
    if (this.customAudioUrl && this.customAudio) {
      this.playCustomAudio();
    } else {
      this.playSynthesizedMelody();
    }
  }

  public pause() {
    this.isPlaying = false;
    if (this.customAudio) {
      this.customAudio.pause();
    }
    if (this.synthTimeoutId) {
      window.clearTimeout(this.synthTimeoutId);
      this.synthTimeoutId = null;
    }
    if (this.progressInterval) {
      window.clearInterval(this.progressInterval);
      this.progressInterval = null;
    }
    this.notify();
  }

  public setCustomAudio(url: string) {
    this.pause();
    this.customAudioUrl = url;
    if (url) {
      this.customAudio = new Audio(url);
      this.customAudio.loop = true;
      this.customAudio.addEventListener('timeupdate', () => {
        if (this.customAudio && this.customAudio.duration) {
          this.progress = (this.customAudio.currentTime / this.customAudio.duration) * 100;
          this.notify();
        }
      });
      this.customAudio.addEventListener('ended', () => {
        this.progress = 0;
        this.notify();
      });
    } else {
      this.customAudio = null;
    }
    this.notify();
  }

  public resetToBuiltIn() {
    this.pause();
    this.customAudioUrl = '';
    this.customAudio = null;
    this.progress = 0;
    this.currentNoteIndex = 0;
    this.notify();
  }

  private playCustomAudio() {
    if (!this.customAudio) return;
    this.customAudio.play().then(() => {
      this.isPlaying = true;
      this.notify();
    }).catch(err => {
      console.warn("Could not play custom audio, falling back to melody:", err);
      this.customAudioUrl = '';
      this.playSynthesizedMelody();
    });
  }

  private initAudioContext() {
    if (!this.audioCtx) {
      const AudioCtxClass = window.AudioContext || (window as unknown as { webkitAudioContext: typeof AudioContext }).webkitAudioContext;
      this.audioCtx = new AudioCtxClass();
    }
    if (this.audioCtx.state === 'suspended') {
      this.audioCtx.resume();
    }
  }

  private playSynthesizedMelody() {
    this.initAudioContext();
    this.isPlaying = true;
    this.notify();

    const playNextNote = () => {
      if (!this.isPlaying || !this.audioCtx) return;

      const note = this.melody[this.currentNoteIndex];
      this.progress = (this.currentNoteIndex / this.melody.length) * 100;
      this.notify();

      this.playChimeTone(note.freq, note.dur / 1000);

      this.currentNoteIndex = (this.currentNoteIndex + 1) % this.melody.length;
      this.synthTimeoutId = window.setTimeout(playNextNote, note.dur + 60);
    };

    playNextNote();
  }

  // Pure music-box harmonic bell chime
  private playChimeTone(frequency: number, durationSeconds: number) {
    if (!this.audioCtx) return;

    const ctx = this.audioCtx;
    const now = ctx.currentTime;

    // Master gain node
    const gainNode = ctx.createGain();
    gainNode.connect(ctx.destination);

    // Exponential bell envelope
    gainNode.gain.setValueAtTime(0.001, now);
    gainNode.gain.linearRampToValueAtTime(0.35, now + 0.02);
    gainNode.gain.exponentialRampToValueAtTime(0.0001, now + durationSeconds);

    // Fundamental sine wave
    const osc1 = ctx.createOscillator();
    osc1.type = 'sine';
    osc1.frequency.setValueAtTime(frequency, now);
    osc1.connect(gainNode);

    // Harmonic bell overtone (octave + fifth gives that magical music box sparkle)
    const osc2 = ctx.createOscillator();
    const gain2 = ctx.createGain();
    gain2.gain.setValueAtTime(0.12, now);
    gain2.gain.exponentialRampToValueAtTime(0.0001, now + durationSeconds * 0.7);

    osc2.type = 'sine';
    osc2.frequency.setValueAtTime(frequency * 2.756, now); // chime harmonic
    osc2.connect(gain2);
    gain2.connect(ctx.destination);

    osc1.start(now);
    osc2.start(now);

    osc1.stop(now + durationSeconds + 0.1);
    osc2.stop(now + durationSeconds + 0.1);
  }
}

export interface AudioPlayerState {
  isPlaying: boolean;
  progress: number;
  isCustom: boolean;
}

export const romanticAudio = new RomanticAudioEngine();
