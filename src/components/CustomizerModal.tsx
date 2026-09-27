import React, { useState } from 'react';
import { Camera, Check, Heart, RotateCcw, Trash2, X } from 'lucide-react';
import { MemoryPhotoItem } from '../config';

interface CustomizerModalProps {
  isOpen: boolean;
  onClose: () => void;
  girlfriendName: string;
  senderName: string;
  letterBody: string;
  birthdayDate: string;
  showCountdown: boolean;
  memories: MemoryPhotoItem[];
  onSave: (data: {
    girlfriendName: string;
    senderName: string;
    letterBody: string;
    birthdayDate: string;
    showCountdown: boolean;
    memories: MemoryPhotoItem[];
  }) => void;
  onReset: () => void;
}

export const CustomizerModal: React.FC<CustomizerModalProps> = ({
  isOpen,
  onClose,
  girlfriendName,
  senderName,
  letterBody,
  birthdayDate,
  showCountdown,
  memories,
  onSave,
  onReset,
}) => {
  const [tempGirlfriendName, setTempGirlfriendName] = useState(girlfriendName);
  const [tempSenderName, setTempSenderName] = useState(senderName);
  const [tempLetterBody, setTempLetterBody] = useState(letterBody);
  const [tempBirthdayDate, setTempBirthdayDate] = useState(() => {
    // format as YYYY-MM-DDTHH:mm for datetime-local input
    try {
      const d = new Date(birthdayDate);
      if (!isNaN(d.getTime())) {
        const offset = d.getTimezoneOffset() * 60000;
        return new Date(d.getTime() - offset).toISOString().slice(0, 16);
      }
    } catch (e) {}
    return '';
  });
  const [tempShowCountdown, setTempShowCountdown] = useState(showCountdown);
  const [tempMemories, setTempMemories] = useState<MemoryPhotoItem[]>(memories);

  if (!isOpen) return null;

  const handlePhotoUpload = (id: number, e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = (event) => {
        const result = event.target?.result as string;
        setTempMemories((prev) =>
          prev.map((m) => (m.id === id ? { ...m, customImage: result } : m))
        );
      };
      reader.readAsDataURL(file);
    }
    e.target.value = '';
  };

  const handleRemovePhoto = (id: number) => {
    setTempMemories((prev) =>
      prev.map((m) => (m.id === id ? { ...m, customImage: '' } : m))
    );
  };

  const handleSave = () => {
    onSave({
      girlfriendName: tempGirlfriendName,
      senderName: tempSenderName,
      letterBody: tempLetterBody,
      birthdayDate: tempBirthdayDate ? new Date(tempBirthdayDate).toISOString() : birthdayDate,
      showCountdown: tempShowCountdown,
      memories: tempMemories,
    });
    onClose();
  };

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/60 backdrop-blur-sm animate-fade-in overflow-y-auto">
      <div className="bg-white rounded-3xl p-6 sm:p-8 max-w-lg w-full shadow-2xl border-2 border-rose-100 max-h-[90vh] overflow-y-auto">
        {/* Modal Header */}
        <div className="flex items-center justify-between pb-4 border-b border-rose-100 mb-6">
          <div className="flex items-center gap-2">
            <Heart className="w-6 h-6 text-rose-600 fill-rose-600" />
            <h3 className="font-serif-romantic text-xl sm:text-2xl font-bold text-rose-950">
              Personalize Your Surprise 💖
            </h3>
          </div>
          <button
            onClick={onClose}
            className="p-1.5 rounded-full hover:bg-rose-50 text-rose-400 hover:text-rose-600 transition"
          >
            <X className="w-5 h-5" />
          </button>
        </div>

        <p className="text-xs text-rose-800/70 mb-5">
          यहाँ आप अपनी गर्लफ्रेंड का नाम, लव लेटर, और अपनी मनपसंद फ़ोटो बिना किसी कोडिंग के आसानी से जोड़ सकते हैं:
        </p>

        {/* Inputs */}
        <div className="space-y-4 mb-6">
          {/* Girlfriend's Name */}
          <div>
            <label className="block text-xs font-semibold text-rose-900 mb-1">
              Girlfriend's Name / Nickname (गर्लफ्रेंड का नाम):
            </label>
            <input
              type="text"
              value={tempGirlfriendName}
              onChange={(e) => setTempGirlfriendName(e.target.value)}
              placeholder="e.g. My Love, Pooja, Simran, My Princess"
              className="w-full px-4 py-2.5 rounded-xl border border-rose-200 focus:outline-none focus:ring-2 focus:ring-rose-500 text-sm"
            />
          </div>

          {/* Sender's Name */}
          <div>
            <label className="block text-xs font-semibold text-rose-900 mb-1">
              Your Name / Sign-off (आपका नाम):
            </label>
            <input
              type="text"
              value={tempSenderName}
              onChange={(e) => setTempSenderName(e.target.value)}
              placeholder="e.g. Forever Yours, Rahul, Aman"
              className="w-full px-4 py-2.5 rounded-xl border border-rose-200 focus:outline-none focus:ring-2 focus:ring-rose-500 text-sm"
            />
          </div>

          {/* Love Letter Body */}
          <div>
            <label className="block text-xs font-semibold text-rose-900 mb-1">
              Love Letter Message (प्यार भरा खत):
            </label>
            <textarea
              rows={4}
              value={tempLetterBody}
              onChange={(e) => setTempLetterBody(e.target.value)}
              placeholder="Write your heartfelt letter here..."
              className="w-full px-4 py-2.5 rounded-xl border border-rose-200 focus:outline-none focus:ring-2 focus:ring-rose-500 text-sm leading-relaxed"
            />
          </div>

          {/* Birthday Date & Time for Countdown */}
          <div className="bg-rose-50/60 p-3.5 rounded-2xl border border-rose-200">
            <div className="flex items-center justify-between mb-2">
              <label className="text-xs font-semibold text-rose-900 flex items-center gap-1.5">
                <span>⏰ Official Birthday Date & Time (जन्मदिन की तारीख/समय):</span>
              </label>
              <label className="inline-flex items-center gap-1.5 cursor-pointer text-xs text-rose-700">
                <input
                  type="checkbox"
                  checked={tempShowCountdown}
                  onChange={(e) => setTempShowCountdown(e.target.checked)}
                  className="rounded text-rose-600 focus:ring-rose-500"
                />
                <span>Show Countdown</span>
              </label>
            </div>
            <input
              type="datetime-local"
              value={tempBirthdayDate}
              onChange={(e) => setTempBirthdayDate(e.target.value)}
              className="w-full px-3.5 py-2 rounded-xl border border-rose-200 focus:outline-none focus:ring-2 focus:ring-rose-500 text-sm bg-white"
            />
            <p className="text-[11px] text-rose-500 mt-1 italic">
              इस तारीख और समय तक पहले स्क्रीन पर घंटों, मिनटों और सेकंडों का लाइव काउंटडाउन चलेगा!
            </p>
          </div>

          {/* 6 Photo Slots */}
          <div>
            <label className="block text-xs font-semibold text-rose-900 mb-2">
              6 Photo Slots (अपनी 6 फ़ोटो जोड़ें):
            </label>
            <div className="grid grid-cols-3 gap-3">
              {tempMemories.map((m, idx) => (
                <div
                  key={m.id}
                  className="relative aspect-square rounded-xl overflow-hidden border border-rose-200 bg-rose-50 flex items-center justify-center group"
                >
                  {m.customImage ? (
                    <>
                      <img
                        src={m.customImage}
                        alt={`Memory ${idx + 1}`}
                        className="w-full h-full object-cover"
                      />
                      <button
                        onClick={() => handleRemovePhoto(m.id)}
                        title="Remove photo"
                        className="absolute top-1 right-1 w-6 h-6 rounded-full bg-rose-600/90 text-white flex items-center justify-center shadow-xs"
                      >
                        <Trash2 className="w-3.5 h-3.5" />
                      </button>
                    </>
                  ) : (
                    <label className="w-full h-full flex flex-col items-center justify-center cursor-pointer p-1 text-center hover:bg-rose-100 transition">
                      <Camera className="w-5 h-5 text-rose-400 mb-0.5" />
                      <span className="text-[10px] font-bold text-rose-700">Photo {idx + 1}</span>
                      <span className="text-[9px] text-rose-400">+ Add</span>
                      <input
                        type="file"
                        accept="image/*"
                        onChange={(e) => handlePhotoUpload(m.id, e)}
                        className="hidden"
                      />
                    </label>
                  )}
                </div>
              ))}
            </div>
          </div>
        </div>

        {/* Modal Actions */}
        <div className="flex items-center justify-between pt-4 border-t border-rose-100 gap-3">
          <button
            onClick={() => {
              onReset();
              onClose();
            }}
            className="px-4 py-2 rounded-xl text-xs font-semibold text-rose-600 hover:bg-rose-50 transition flex items-center gap-1.5"
          >
            <RotateCcw className="w-3.5 h-3.5" />
            <span>Reset Defaults</span>
          </button>

          <button
            onClick={handleSave}
            className="px-6 py-2.5 rounded-xl bg-gradient-to-r from-rose-600 to-pink-500 text-white font-semibold text-sm shadow-md hover:shadow-lg transition flex items-center gap-2"
          >
            <Check className="w-4 h-4" />
            <span>Save & Apply ✨</span>
          </button>
        </div>
      </div>
    </div>
  );
};
