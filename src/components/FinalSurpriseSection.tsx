import React, { useState } from 'react';
import { Edit3, Heart, RotateCcw, Sparkles, Star } from 'lucide-react';
import { BIRTHDAY_CONFIG } from '../config';
import { ConfettiEffect, triggerConfettiBurst } from './Confetti';
import { Balloons } from './Balloons';

interface FinalSurpriseSectionProps {
  girlfriendName: string;
  senderName: string;
  onReplay: () => void;
  onOpenCustomizer: () => void;
}

export const FinalSurpriseSection: React.FC<FinalSurpriseSectionProps> = ({
  girlfriendName,
  senderName,
  onReplay,
  onOpenCustomizer,
}) => {
  const [isRevealed, setIsRevealed] = useState(false);

  const handleReveal = () => {
    setIsRevealed(true);
    triggerConfettiBurst();
  };

  return (
    <div className="relative min-h-[85vh] py-8 px-4 max-w-md mx-auto z-10 flex flex-col items-center justify-center text-center">
      {/* Visual Effects upon reveal */}
      {isRevealed && (
        <>
          <ConfettiEffect continuous={true} />
          <Balloons count={10} />
        </>
      )}

      {!isRevealed ? (
        /* BEFORE REVEAL: Suspense */
        <div className="w-full animate-fade-in">
          {/* Glowing Suspense Orb */}
          <div className="relative inline-block mb-8">
            <div className="w-28 h-28 rounded-full bg-gradient-to-tr from-amber-400 via-rose-500 to-pink-500 p-1 shadow-2xl flex items-center justify-center animate-bounce">
              <div className="w-full h-full rounded-full bg-white flex items-center justify-center shadow-inner">
                <Sparkles className="w-14 h-14 text-rose-500" />
              </div>
            </div>
          </div>

          <h2 className="font-serif-romantic text-3xl sm:text-4xl font-extrabold text-rose-950 mb-3">
            {BIRTHDAY_CONFIG.finalSurprise.preTitle}
          </h2>

          <p className="text-rose-900/80 text-base sm:text-lg font-light leading-relaxed mb-8 italic">
            Before your special day moves on, there is one last heartfelt promise waiting for you...
          </p>

          <button
            onClick={handleReveal}
            className="w-full sm:w-auto px-10 py-4 rounded-full bg-gradient-to-r from-rose-600 via-rose-500 to-pink-500 text-white font-semibold text-lg tracking-wide shadow-xl shadow-rose-500/30 transform transition duration-300 hover:scale-105 active:scale-95 inline-flex items-center justify-center gap-3 border border-white/30"
          >
            <Heart className="w-5 h-5 fill-white" />
            <span>{BIRTHDAY_CONFIG.finalSurprise.revealButtonText}</span>
          </button>
        </div>
      ) : (
        /* AFTER REVEAL: Grand Emotional Finale */
        <div className="w-full animate-scale-up">
          {/* Glowing Neon Love Heart */}
          <div className="relative inline-block mb-6">
            <div className="w-28 h-28 rounded-full bg-gradient-to-tr from-rose-600 to-red-500 p-1 shadow-2xl flex items-center justify-center animate-heart-pulse">
              <Heart className="w-16 h-16 text-white fill-white" />
            </div>
            <div className="absolute -top-2 -right-2 text-2xl">✨</div>
            <div className="absolute -bottom-2 -left-2 text-2xl">💖</div>
          </div>

          {/* Grand Message Card */}
          <div className="w-full bg-white/95 backdrop-blur-md rounded-3xl p-7 sm:p-8 shadow-2xl border-2 border-rose-100 mb-8">
            <div className="inline-flex items-center gap-1.5 px-3 py-1 rounded-full bg-rose-50 text-rose-600 text-xs font-bold tracking-widest uppercase mb-4">
              <Star className="w-3.5 h-3.5 fill-amber-400 text-amber-400" />
              <span>Forever & Always</span>
              <Star className="w-3.5 h-3.5 fill-amber-400 text-amber-400" />
            </div>

            <h2 className="font-serif-romantic text-3xl sm:text-4xl font-extrabold text-rose-950 mb-4 leading-tight">
              Happy Birthday,
              <span className="block mt-1 bg-gradient-to-r from-rose-600 to-pink-500 bg-clip-text text-transparent">
                {girlfriendName || "My Love"} ❤️
              </span>
            </h2>

            <p className="text-rose-950/90 text-base sm:text-lg font-light leading-relaxed mb-6">
              "{BIRTHDAY_CONFIG.finalSurprise.message}"
            </p>

            <div className="pt-4 border-t border-rose-100 flex flex-col items-center">
              <span className="text-xs text-rose-400 italic mb-0.5">Always in my heart</span>
              <span className="font-handwriting text-2xl text-rose-700 font-bold">
                — {senderName || "Forever Yours"} ❤️
              </span>
            </div>
          </div>

          {/* Replay Surprise Button */}
          <div className="flex flex-col items-center gap-3">
            <button
              onClick={onReplay}
              className="w-full sm:w-auto px-10 py-4 rounded-full bg-gradient-to-r from-rose-600 via-rose-500 to-pink-500 text-white font-semibold text-lg tracking-wide shadow-lg shadow-rose-500/30 transform transition duration-300 hover:scale-105 active:scale-95 inline-flex items-center justify-center gap-3 border border-white/30"
            >
              <RotateCcw className="w-5 h-5" />
              <span>{BIRTHDAY_CONFIG.finalSurprise.replayButtonText}</span>
            </button>

            {/* Quick in-browser customize option */}
            <button
              onClick={onOpenCustomizer}
              className="inline-flex items-center gap-1.5 px-4 py-2 rounded-full text-xs font-semibold text-rose-600 hover:bg-rose-50 transition"
            >
              <Edit3 className="w-3.5 h-3.5" />
              <span>Personalize Name, Photos & Message</span>
            </button>
          </div>
        </div>
      )}

      <style>{`
        @keyframes scaleUp {
          from {
            opacity: 0;
            transform: scale(0.9);
          }
          to {
            opacity: 1;
            transform: scale(1);
          }
        }
        .animate-scale-up {
          animation: scaleUp 0.7s cubic-bezier(0.16, 1, 0.3, 1) forwards;
        }
      `}</style>
    </div>
  );
};
