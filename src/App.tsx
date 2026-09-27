import React, { useState, useEffect } from 'react';
import { Edit3, Heart, Music, Pause, Play, Share2, Sparkles } from 'lucide-react';
import { BIRTHDAY_CONFIG, MemoryPhotoItem } from './config';
import { FloatingHearts } from './components/FloatingHearts';
import { IntroSection } from './components/IntroSection';
import { RevealSection } from './components/RevealSection';
import { MemoriesSection } from './components/MemoriesSection';
import { LoveLetterSection } from './components/LoveLetterSection';
import { MusicSection } from './components/MusicSection';
import { FinalSurpriseSection } from './components/FinalSurpriseSection';
import { CustomizerModal } from './components/CustomizerModal';
import { ShareModal } from './components/ShareModal';
import { romanticAudio, AudioPlayerState } from './utils/audio';

export const App: React.FC = () => {
  // Current active step (0 to 5)
  const [currentStep, setCurrentStep] = useState<number>(0);
  const [isCustomizerOpen, setIsCustomizerOpen] = useState<boolean>(false);
  const [isShareModalOpen, setIsShareModalOpen] = useState<boolean>(false);

  // Audio State
  const [playerState, setPlayerState] = useState<AudioPlayerState>({
    isPlaying: false,
    progress: 0,
    isCustom: false,
  });

  // State loaded from URL params, localStorage or default config
  const [girlfriendName, setGirlfriendName] = useState<string>(() => {
    const urlParams = new URLSearchParams(window.location.search);
    const fromUrl = urlParams.get('to') || urlParams.get('name');
    if (fromUrl) return decodeURIComponent(fromUrl);
    const saved = localStorage.getItem('bday_gf_name');
    if (saved && saved !== 'My Love') return saved;
    return BIRTHDAY_CONFIG.girlfriendName;
  });

  const [senderName, setSenderName] = useState<string>(() => {
    const urlParams = new URLSearchParams(window.location.search);
    const fromUrl = urlParams.get('from');
    if (fromUrl) return decodeURIComponent(fromUrl);
    const saved = localStorage.getItem('bday_sender_name');
    if (saved && saved !== 'Forever Yours') return saved;
    return BIRTHDAY_CONFIG.senderName;
  });

  const [letterBody, setLetterBody] = useState<string>(() => {
    const saved = localStorage.getItem('bday_letter_body');
    if (saved && !saved.includes("Today is all about you")) return saved;
    return BIRTHDAY_CONFIG.letter.body;
  });

  const [birthdayDate, setBirthdayDate] = useState<string>(() => {
    return localStorage.getItem('bday_target_date') || BIRTHDAY_CONFIG.birthdayDate;
  });

  const [showCountdown, setShowCountdown] = useState<boolean>(() => {
    const saved = localStorage.getItem('bday_show_countdown');
    return saved !== null ? saved === 'true' : BIRTHDAY_CONFIG.showCountdown;
  });

  const [memories, setMemories] = useState<MemoryPhotoItem[]>(() => {
    const saved = localStorage.getItem('bday_memories');
    if (saved) {
      try {
        const parsed = JSON.parse(saved);
        const hasCustomUserImages = parsed.some((m: MemoryPhotoItem) => m.customImage && m.customImage.length > 0);
        if (hasCustomUserImages) {
          return BIRTHDAY_CONFIG.memories.map((m, idx) => ({
            ...m,
            customImage: parsed[idx]?.customImage || m.customImage
          }));
        }
      } catch (e) {
        // fallback
      }
    }
    return BIRTHDAY_CONFIG.memories;
  });

  // Subscribe to audio engine updates
  useEffect(() => {
    const unsubscribe = romanticAudio.subscribe((state) => {
      setPlayerState({ ...state });
    });
    return () => {
      unsubscribe();
      romanticAudio.pause();
    };
  }, []);

  // Update single photo memory
  const handleUpdateMemoryPhoto = (id: number, dataUrl: string) => {
    const updated = memories.map((m) =>
      m.id === id ? { ...m, customImage: dataUrl } : m
    );
    setMemories(updated);
    try {
      localStorage.setItem('bday_memories', JSON.stringify(updated));
    } catch (e) {
      console.warn("Storage quota limit reached for images:", e);
    }
  };

  // Save all customizer changes
  const handleSaveCustomization = (data: {
    girlfriendName: string;
    senderName: string;
    letterBody: string;
    birthdayDate: string;
    showCountdown: boolean;
    memories: MemoryPhotoItem[];
  }) => {
    setGirlfriendName(data.girlfriendName);
    setSenderName(data.senderName);
    setLetterBody(data.letterBody);
    setBirthdayDate(data.birthdayDate);
    setShowCountdown(data.showCountdown);
    setMemories(data.memories);

    localStorage.setItem('bday_gf_name', data.girlfriendName);
    localStorage.setItem('bday_sender_name', data.senderName);
    localStorage.setItem('bday_letter_body', data.letterBody);
    localStorage.setItem('bday_target_date', data.birthdayDate);
    localStorage.setItem('bday_show_countdown', String(data.showCountdown));
    try {
      localStorage.setItem('bday_memories', JSON.stringify(data.memories));
    } catch (e) {
      console.warn("Storage quota limit for photos:", e);
    }
  };

  const handleResetDefaults = () => {
    localStorage.removeItem('bday_gf_name');
    localStorage.removeItem('bday_sender_name');
    localStorage.removeItem('bday_letter_body');
    localStorage.removeItem('bday_target_date');
    localStorage.removeItem('bday_show_countdown');
    localStorage.removeItem('bday_memories');

    setGirlfriendName(BIRTHDAY_CONFIG.girlfriendName);
    setSenderName(BIRTHDAY_CONFIG.senderName);
    setLetterBody(BIRTHDAY_CONFIG.letter.body);
    setBirthdayDate(BIRTHDAY_CONFIG.birthdayDate);
    setShowCountdown(BIRTHDAY_CONFIG.showCountdown);
    setMemories(BIRTHDAY_CONFIG.memories);
    romanticAudio.resetToBuiltIn();
  };

  const stepTitles = [
    "A Surprise 🎁",
    "Birthday Reveal 🎉",
    "Our Memories 📸",
    "Love Letter 💌",
    "Our Song 🎶",
    "Forever ✨"
  ];

  return (
    <div className="min-h-screen bg-gradient-to-b from-[#FFF8F6] via-[#FFF1F3] to-[#FFF8F6] text-[#3E1F27] flex flex-col justify-between relative selection:bg-rose-200">
      {/* Background Floating Hearts */}
      <FloatingHearts />

      {/* Top Floating App Bar */}
      <header className="sticky top-0 z-40 backdrop-blur-md bg-white/70 border-b border-rose-100/80 px-4 py-2.5 transition-all">
        <div className="max-w-md mx-auto flex items-center justify-between">
          {/* Step Progress Indicators */}
          <div className="flex items-center gap-1.5">
            {stepTitles.map((title, idx) => (
              <button
                key={idx}
                onClick={() => setCurrentStep(idx)}
                title={title}
                className={`h-2 rounded-full transition-all duration-300 ${
                  idx === currentStep
                    ? 'w-7 bg-rose-600 shadow-xs'
                    : idx < currentStep
                    ? 'w-2.5 bg-rose-300'
                    : 'w-2 bg-rose-100'
                }`}
              />
            ))}
          </div>

          {/* Right Action Icons: Mini Audio Controller & Edit Button */}
          <div className="flex items-center gap-2">
            {/* Quick Audio Toggle Pill */}
            <button
              onClick={() => romanticAudio.togglePlay()}
              className={`inline-flex items-center gap-1.5 px-3 py-1 rounded-full text-xs font-semibold shadow-xs transition duration-300 ${
                playerState.isPlaying
                  ? 'bg-rose-500 text-white shadow-rose-300 animate-pulse'
                  : 'bg-white text-rose-700 border border-rose-200 hover:bg-rose-50'
              }`}
            >
              {playerState.isPlaying ? (
                <>
                  <Pause className="w-3 h-3 fill-white" />
                  <span>Music Playing</span>
                </>
              ) : (
                <>
                  <Music className="w-3 h-3 text-rose-600" />
                  <span>Play Music</span>
                </>
              )}
            </button>

            {/* In-browser Customizer Button */}
            <button
              onClick={() => setIsCustomizerOpen(true)}
              title="Personalize surprise (गर्लफ्रेंड का नाम और फ़ोटो बदलें)"
              className="p-1.5 rounded-full bg-white border border-rose-200 text-rose-600 hover:bg-rose-50 transition shadow-xs"
            >
              <Edit3 className="w-4 h-4" />
            </button>

            {/* Share Link Button */}
            <button
              onClick={() => setIsShareModalOpen(true)}
              title="Share Permanent Link 🔗"
              className="px-2.5 py-1 rounded-full bg-gradient-to-r from-rose-600 to-pink-500 text-white font-medium text-xs flex items-center gap-1 shadow-xs hover:shadow-md transition"
            >
              <Share2 className="w-3.5 h-3.5" />
              <span>Share 🔗</span>
            </button>
          </div>
        </div>
      </header>

      {/* Main Section Content Area */}
      <main className="flex-1 flex flex-col justify-center">
        {currentStep === 0 && (
          <IntroSection
            onOpenSurprise={() => setCurrentStep(1)}
            birthdayDate={birthdayDate}
            showCountdown={showCountdown}
          />
        )}

        {currentStep === 1 && (
          <RevealSection
            girlfriendName={girlfriendName}
            onContinue={() => setCurrentStep(2)}
          />
        )}

        {currentStep === 2 && (
          <MemoriesSection
            memories={memories}
            onUpdatePhoto={handleUpdateMemoryPhoto}
            onContinue={() => setCurrentStep(3)}
          />
        )}

        {currentStep === 3 && (
          <LoveLetterSection
            girlfriendName={girlfriendName}
            senderName={senderName}
            customLetterBody={letterBody}
            onContinue={() => setCurrentStep(4)}
          />
        )}

        {currentStep === 4 && (
          <MusicSection
            playerState={playerState}
            onTogglePlay={() => romanticAudio.togglePlay()}
            onCustomAudioFile={(url) => romanticAudio.setCustomAudio(url)}
            onResetAudio={() => romanticAudio.resetToBuiltIn()}
            onContinue={() => setCurrentStep(5)}
          />
        )}

        {currentStep === 5 && (
          <FinalSurpriseSection
            girlfriendName={girlfriendName}
            senderName={senderName}
            onReplay={() => setCurrentStep(0)}
            onOpenCustomizer={() => setIsCustomizerOpen(true)}
          />
        )}
      </main>

      {/* Subtle Romantic Footer */}
      <footer className="relative z-10 py-3 text-center text-xs text-rose-400 font-light">
        <p className="flex items-center justify-center gap-1">
          <span>Crafted with endless love for {girlfriendName || "My Love"}</span>
          <Heart className="w-3.5 h-3.5 text-rose-500 fill-rose-500" />
        </p>
      </footer>

      {/* Live Customizer Modal */}
      <CustomizerModal
        isOpen={isCustomizerOpen}
        onClose={() => setIsCustomizerOpen(false)}
        girlfriendName={girlfriendName}
        senderName={senderName}
        letterBody={letterBody}
        birthdayDate={birthdayDate}
        showCountdown={showCountdown}
        memories={memories}
        onSave={handleSaveCustomization}
        onReset={handleResetDefaults}
      />

      {/* Permanent Share Modal */}
      <ShareModal
        isOpen={isShareModalOpen}
        onClose={() => setIsShareModalOpen(false)}
        permanentUrl={
          typeof window !== 'undefined'
            ? `${window.location.origin.replace('ais-dev-', 'ais-pre-')}${window.location.pathname}?to=${encodeURIComponent(
                girlfriendName
              )}&from=${encodeURIComponent(senderName)}`
            : 'https://ais-pre-stcgg4gt6zl6hsfmzfpwbk-708503395535.asia-east1.run.app'
        }
      />
    </div>
  );
};

export default App;
