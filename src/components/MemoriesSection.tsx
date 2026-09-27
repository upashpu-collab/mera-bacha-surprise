import React, { useRef } from 'react';
import { ArrowRight, Camera, Heart, Sparkles, Upload } from 'lucide-react';
import { MemoryPhotoItem } from '../config';

interface MemoriesSectionProps {
  memories: MemoryPhotoItem[];
  onUpdatePhoto: (id: number, dataUrl: string) => void;
  onContinue: () => void;
}

export const MemoriesSection: React.FC<MemoriesSectionProps> = ({
  memories,
  onUpdatePhoto,
  onContinue,
}) => {
  const fileInputRef = useRef<HTMLInputElement>(null);
  const activePhotoIdRef = useRef<number | null>(null);

  const handlePhotoUploadClick = (id: number) => {
    activePhotoIdRef.current = id;
    fileInputRef.current?.click();
  };

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (file && activePhotoIdRef.current !== null) {
      const reader = new FileReader();
      reader.onload = (event) => {
        const result = event.target?.result as string;
        if (result && activePhotoIdRef.current !== null) {
          onUpdatePhoto(activePhotoIdRef.current, result);
        }
      };
      reader.readAsDataURL(file);
    }
    // reset input
    e.target.value = '';
  };

  return (
    <div className="relative min-h-[85vh] py-8 px-4 max-w-xl mx-auto z-10">
      {/* Hidden file input for uploading images */}
      <input
        type="file"
        ref={fileInputRef}
        onChange={handleFileChange}
        accept="image/*"
        className="hidden"
      />

      {/* Header */}
      <div className="text-center mb-8">
        <div className="inline-flex items-center gap-1.5 px-3 py-1 rounded-full bg-rose-50 text-rose-600 text-xs font-semibold tracking-wider uppercase mb-2">
          <Heart className="w-3.5 h-3.5 fill-rose-500 text-rose-500" />
          <span>Our Cherished Moments</span>
          <Heart className="w-3.5 h-3.5 fill-rose-500 text-rose-500" />
        </div>

        <h2 className="font-serif-romantic text-2xl sm:text-3xl font-bold text-rose-950 mb-2">
          Moments That Made Me Fall in Love ✨
        </h2>

        <p className="text-rose-900/70 text-sm italic">
          Tap on any photo card below to replace it with your real picture! 📸
        </p>
      </div>

      {/* 6 Polaroid-Style Memory Cards */}
      <div className="grid grid-cols-1 sm:grid-cols-2 gap-6 mb-10">
        {memories.map((m, index) => {
          const rotation = index % 2 === 0 ? '-rotate-1' : 'rotate-1';
          return (
            <div
              key={m.id}
              className={`bg-white rounded-2xl p-4 shadow-xl border border-rose-100/80 transform transition duration-300 hover:rotate-0 hover:scale-[1.02] flex flex-col justify-between ${rotation}`}
            >
              {/* Tape Effect at Top */}
              <div className="w-16 h-3 bg-amber-200/60 rounded-sm mx-auto -mt-2 mb-3 shadow-xs" />

              {/* Photo Area */}
              <div
                onClick={() => handlePhotoUploadClick(m.id)}
                className="relative w-full aspect-[4/3] rounded-xl overflow-hidden cursor-pointer group bg-gradient-to-tr from-rose-100 to-pink-50 flex items-center justify-center border border-rose-100"
              >
                {m.customImage ? (
                  <img
                    src={m.customImage}
                    alt={m.title}
                    className="w-full h-full object-cover transition duration-500 group-hover:scale-105"
                  />
                ) : (
                  /* Romantic Placeholder without copyright external image issues */
                  <div className={`w-full h-full bg-gradient-to-tr ${m.gradient} flex flex-col items-center justify-center p-4 text-white text-center shadow-inner relative`}>
                    <span className="text-4xl mb-1 transform transition duration-300 group-hover:scale-125">
                      {m.emoji}
                    </span>
                    <span className="text-sm font-semibold tracking-wide drop-shadow-sm">
                      {m.title}
                    </span>
                    <span className="text-[11px] opacity-90 font-light mt-1 bg-white/20 px-2 py-0.5 rounded-full backdrop-blur-xs flex items-center gap-1">
                      <Camera className="w-3 h-3" />
                      <span>Tap to add photo</span>
                    </span>
                  </div>
                )}

                {/* Hover overlay hint */}
                <div className="absolute inset-0 bg-rose-950/40 opacity-0 group-hover:opacity-100 transition duration-300 flex items-center justify-center backdrop-blur-[2px]">
                  <div className="bg-white text-rose-600 px-3 py-1.5 rounded-full text-xs font-bold flex items-center gap-1.5 shadow-lg">
                    <Upload className="w-3.5 h-3.5" />
                    <span>{m.customImage ? 'Change Photo' : 'Upload Photo'}</span>
                  </div>
                </div>

                {/* Number Badge */}
                <div className="absolute top-2 right-2 w-6 h-6 rounded-full bg-white/90 text-rose-700 text-xs font-bold flex items-center justify-center shadow-sm">
                  {index + 1}
                </div>
              </div>

              {/* Caption */}
              <div className="pt-3 pb-1 text-center">
                <p className="font-handwriting text-lg text-rose-950 font-semibold leading-tight mb-1">
                  {m.title}
                </p>
                <p className="text-xs text-rose-900/70 font-light italic leading-relaxed">
                  "{m.caption}"
                </p>
              </div>
            </div>
          );
        })}
      </div>

      {/* Continue Button */}
      <div className="text-center pb-6">
        <button
          onClick={onContinue}
          className="w-full sm:w-auto px-10 py-4 rounded-full bg-gradient-to-r from-rose-600 via-rose-500 to-pink-500 text-white font-semibold text-lg tracking-wide shadow-lg shadow-rose-500/30 transform transition duration-300 hover:scale-105 active:scale-95 inline-flex items-center justify-center gap-3 border border-white/30"
        >
          <span>Open Your Love Letter 💌</span>
          <ArrowRight className="w-5 h-5" />
        </button>
      </div>
    </div>
  );
};
