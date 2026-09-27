import React from 'react';
import { ArrowRight, Cake, Sparkles, Heart } from 'lucide-react';
import { BIRTHDAY_CONFIG } from '../config';
import { ConfettiEffect } from './Confetti';
import { Balloons } from './Balloons';

interface RevealSectionProps {
  girlfriendName: string;
  onContinue: () => void;
}

export const RevealSection: React.FC<RevealSectionProps> = ({
  girlfriendName,
  onContinue,
}) => {
  return (
    <div className="relative min-h-[85vh] flex flex-col items-center justify-center px-5 py-8 text-center z-10 max-w-md mx-auto">
      {/* Visual Effects */}
      <Balloons count={11} />
      <ConfettiEffect continuous={true} />

      {/* Main Reveal Content with smooth slide-up animation */}
      <div className="w-full relative z-20 animate-fade-in-up">
        {/* Birthday Cake Emblem */}
        <div className="inline-block relative mb-6">
          <div className="w-24 h-24 rounded-full bg-gradient-to-tr from-rose-500 via-rose-400 to-pink-300 p-1 shadow-2xl flex items-center justify-center animate-heart-pulse">
            <div className="w-full h-full rounded-full bg-white flex items-center justify-center">
              <Cake className="w-12 h-12 text-rose-600 stroke-[1.8]" />
            </div>
          </div>
          <div className="absolute -bottom-1 -right-1 text-2xl animate-bounce">
            🎈
          </div>
        </div>

        {/* Celebration Card */}
        <div className="w-full bg-white/95 backdrop-blur-md rounded-3xl p-7 sm:p-8 shadow-2xl border border-rose-100 mb-8">
          <div className="inline-flex items-center gap-1.5 px-3 py-1 rounded-full bg-rose-50 text-rose-600 text-xs font-bold tracking-widest uppercase mb-4">
            <Sparkles className="w-3.5 h-3.5 text-amber-500" />
            <span>It's Your Special Day!</span>
            <Sparkles className="w-3.5 h-3.5 text-amber-500" />
          </div>

          <h2 className="font-serif-romantic text-3xl sm:text-4xl font-extrabold text-rose-950 mb-3 leading-tight">
            Happy Birthday,
            <span className="block mt-1 bg-gradient-to-r from-rose-600 to-pink-500 bg-clip-text text-transparent">
              {girlfriendName || "My Love"} ❤️
            </span>
          </h2>

          <p className="text-rose-900/80 text-base sm:text-lg font-light leading-relaxed mb-5 italic">
            "{BIRTHDAY_CONFIG.reveal.subtitle}"
          </p>

          {/* Birthday Icons Row */}
          <div className="flex items-center justify-center gap-4 text-2xl pt-2 border-t border-rose-100">
            <span>🎂</span>
            <span>✨</span>
            <span>💖</span>
            <span>🥂</span>
            <span>🎁</span>
          </div>
        </div>

        {/* Continue Button */}
        <button
          onClick={onContinue}
          className="w-full sm:w-auto px-10 py-4 rounded-full bg-gradient-to-r from-rose-600 via-rose-500 to-pink-500 text-white font-semibold text-lg tracking-wide shadow-lg shadow-rose-500/30 transform transition duration-300 hover:scale-105 active:scale-95 flex items-center justify-center gap-3 border border-white/30 mx-auto"
        >
          <span>{BIRTHDAY_CONFIG.reveal.buttonText}</span>
          <ArrowRight className="w-5 h-5" />
        </button>
      </div>

      <style>{`
        @keyframes fadeInUp {
          from {
            opacity: 0;
            transform: translateY(24px) scale(0.96);
          }
          to {
            opacity: 1;
            transform: translateY(0) scale(1);
          }
        }
        .animate-fade-in-up {
          animation: fadeInUp 0.8s cubic-bezier(0.16, 1, 0.3, 1) forwards;
        }
      `}</style>
    </div>
  );
};
