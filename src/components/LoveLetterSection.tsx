import React, { useState, useEffect } from 'react';
import { ArrowRight, Heart, Mail, RotateCcw, Sparkles } from 'lucide-react';
import { BIRTHDAY_CONFIG } from '../config';

interface LoveLetterSectionProps {
  girlfriendName: string;
  senderName: string;
  customLetterBody?: string;
  onContinue: () => void;
}

export const LoveLetterSection: React.FC<LoveLetterSectionProps> = ({
  girlfriendName,
  senderName,
  customLetterBody,
  onContinue,
}) => {
  const [isOpen, setIsOpen] = useState(false);
  const [displayedText, setDisplayedText] = useState('');
  const [isTypingDone, setIsTypingDone] = useState(false);

  const fullMessage = customLetterBody || BIRTHDAY_CONFIG.letter.body;

  // Typewriter effect with smooth chunk typing for long letters
  useEffect(() => {
    if (!isOpen) return;

    setDisplayedText('');
    setIsTypingDone(false);

    let currentIndex = 0;
    // For long letters, advance by multiple characters so reading feels natural
    const step = fullMessage.length > 500 ? 5 : 1;
    const interval = setInterval(() => {
      currentIndex += step;
      if (currentIndex >= fullMessage.length) {
        setDisplayedText(fullMessage);
        setIsTypingDone(true);
        clearInterval(interval);
      } else {
        setDisplayedText(fullMessage.slice(0, currentIndex));
      }
    }, 20);

    return () => clearInterval(interval);
  }, [isOpen, fullMessage]);

  const handleOpenEnvelope = () => {
    if (!isOpen) {
      setIsOpen(true);
    }
  };

  const handleShowFullImmediately = () => {
    setDisplayedText(fullMessage);
    setIsTypingDone(true);
  };

  const handleRetype = () => {
    setDisplayedText('');
    setIsTypingDone(false);
    let currentIndex = 0;
    const step = fullMessage.length > 500 ? 5 : 1;
    const interval = setInterval(() => {
      currentIndex += step;
      if (currentIndex >= fullMessage.length) {
        setDisplayedText(fullMessage);
        setIsTypingDone(true);
        clearInterval(interval);
      } else {
        setDisplayedText(fullMessage.slice(0, currentIndex));
      }
    }, 20);
  };

  return (
    <div className="relative min-h-[85vh] py-8 px-4 max-w-lg mx-auto z-10 flex flex-col items-center">
      {/* Header */}
      <div className="text-center mb-6">
        <div className="inline-flex items-center gap-1.5 px-3 py-1 rounded-full bg-rose-50 text-rose-600 text-xs font-semibold tracking-wider uppercase mb-2">
          <Mail className="w-3.5 h-3.5 text-rose-500" />
          <span>Private & Confidential</span>
          <Mail className="w-3.5 h-3.5 text-rose-500" />
        </div>

        <h2 className="font-serif-romantic text-2xl sm:text-3xl font-bold text-rose-950 mb-1">
          {BIRTHDAY_CONFIG.letter.title || "A Letter From My Heart 💌"}
        </h2>

        <p className="text-rose-900/70 text-sm italic">
          {isOpen
            ? `Written especially for ${girlfriendName || 'Meri Buttki'}`
            : BIRTHDAY_CONFIG.letter.envelopePrompt}
        </p>
      </div>

      {/* Envelope & Letter Container */}
      <div className="w-full relative flex flex-col items-center mb-8">
        {!isOpen ? (
          /* CLOSED ENVELOPE WITH WAX SEAL */
          <div
            onClick={handleOpenEnvelope}
            className="w-full max-w-sm h-60 bg-gradient-to-b from-[#FFF0F3] to-[#FFE4E8] rounded-2xl shadow-2xl border-2 border-rose-200 relative cursor-pointer group transform transition duration-500 hover:scale-105 active:scale-95 flex items-center justify-center overflow-hidden"
          >
            {/* Top Flap Triangle */}
            <div
              className="absolute top-0 left-0 right-0 h-28 bg-[#FFD6DE] border-b-2 border-rose-200 origin-top transform transition duration-700"
              style={{
                clipPath: 'polygon(0 0, 100% 0, 50% 100%)',
              }}
            />

            {/* Bottom Folding Triangle */}
            <div
              className="absolute bottom-0 left-0 right-0 h-32 bg-[#FFC5D2] border-t border-rose-200/50"
              style={{
                clipPath: 'polygon(0 100%, 100% 100%, 50% 0)',
              }}
            />

            {/* Wax Seal with Heart */}
            <div className="relative z-10 w-16 h-16 rounded-full bg-gradient-to-tr from-rose-700 to-rose-500 border-2 border-amber-300 shadow-xl flex items-center justify-center transform transition duration-300 group-hover:scale-110 animate-pulse">
              <Heart className="w-8 h-8 text-white fill-white" />
            </div>

            {/* Tap Prompt */}
            <div className="absolute bottom-3 left-0 right-0 text-center z-10">
              <span className="text-xs font-bold text-rose-800 tracking-wider bg-white/70 px-3 py-1 rounded-full backdrop-blur-xs shadow-xs">
                ✨ TAP TO OPEN ENVELOPE ✨
              </span>
            </div>
          </div>
        ) : (
          /* OPENED LETTER PARCHMENT CARD */
          <div className="w-full bg-[#FFFDFD] rounded-3xl p-6 sm:p-8 shadow-2xl border-2 border-rose-100 relative animate-slide-up">
            {/* Letter Header */}
            <div className="flex items-center justify-between border-b border-rose-100 pb-3 mb-5">
              <div className="flex items-center gap-2">
                <Heart className="w-5 h-5 text-rose-500 fill-rose-500" />
                <span className="font-handwriting text-2xl text-rose-900 font-bold">
                  {BIRTHDAY_CONFIG.letter.salutation || `Dear ${girlfriendName || "Meri Buttki"},`}
                </span>
              </div>
              <div className="flex items-center gap-2">
                {!isTypingDone && (
                  <button
                    onClick={handleShowFullImmediately}
                    className="text-xs px-2.5 py-1 rounded-full bg-rose-50 hover:bg-rose-100 text-rose-600 font-medium transition"
                  >
                    Show All ✨
                  </button>
                )}
                <Sparkles className="w-4 h-4 text-amber-400" />
              </div>
            </div>

            {/* Typewriter Text Body */}
            <div className="min-h-[160px] text-rose-950 font-serif-romantic text-base sm:text-lg leading-relaxed whitespace-pre-line">
              {displayedText}
              {!isTypingDone && (
                <span className="inline-block w-1.5 h-5 bg-rose-600 ml-1 animate-pulse" />
              )}
            </div>

            {/* Sign-off */}
            {isTypingDone && (
              <div className="mt-8 pt-4 border-t border-rose-100/80 flex flex-col items-end animate-fade-in">
                <p className="font-handwriting text-2xl text-rose-600 font-bold mt-1 text-right whitespace-pre-line">
                  {BIRTHDAY_CONFIG.letter.closing || `${senderName || "Upash"} ❤️`}
                </p>
              </div>
            )}

            {/* Read Again button */}
            {isTypingDone && (
              <div className="mt-6 flex justify-center">
                <button
                  onClick={handleRetype}
                  className="inline-flex items-center gap-1.5 px-3 py-1.5 rounded-full bg-rose-50 hover:bg-rose-100 text-rose-600 text-xs font-semibold transition"
                >
                  <RotateCcw className="w-3.5 h-3.5" />
                  <span>Read Letter Again</span>
                </button>
              </div>
            )}
          </div>
        )}
      </div>

      {/* Continue to Music Button */}
      {isOpen && (
        <div className="text-center pb-6 animate-fade-in">
          <button
            onClick={onContinue}
            className="w-full sm:w-auto px-10 py-4 rounded-full bg-gradient-to-r from-rose-600 via-rose-500 to-pink-500 text-white font-semibold text-lg tracking-wide shadow-lg shadow-rose-500/30 transform transition duration-300 hover:scale-105 active:scale-95 inline-flex items-center justify-center gap-3 border border-white/30"
          >
            <span>Play Our Special Music 🎵</span>
            <ArrowRight className="w-5 h-5" />
          </button>
        </div>
      )}

      <style>{`
        @keyframes slideUp {
          from {
            opacity: 0;
            transform: translateY(30px);
          }
          to {
            opacity: 1;
            transform: translateY(0);
          }
        }
        .animate-slide-up {
          animation: slideUp 0.6s cubic-bezier(0.16, 1, 0.3, 1) forwards;
        }
        @keyframes fadeIn {
          from { opacity: 0; }
          to { opacity: 1; }
        }
        .animate-fade-in {
          animation: fadeIn 0.8s ease-in forwards;
        }
      `}</style>
    </div>
  );
};
