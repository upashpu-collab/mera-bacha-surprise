import React, { useState } from 'react';
import { Share2, Copy, Check, ExternalLink, ShieldCheck, Heart, Sparkles } from 'lucide-react';

interface ShareModalProps {
  isOpen: boolean;
  onClose: () => void;
  permanentUrl: string;
}

export const ShareModal: React.FC<ShareModalProps> = ({ isOpen, onClose, permanentUrl }) => {
  const [copied, setCopied] = useState(false);

  if (!isOpen) return null;

  const handleCopy = () => {
    navigator.clipboard.writeText(permanentUrl);
    setCopied(true);
    setTimeout(() => setCopied(false), 2500);
  };

  const handleShareNative = async () => {
    if (navigator.share) {
      try {
        await navigator.share({
          title: "A Birthday Surprise For You ❤️",
          text: "Open your special birthday surprise gift! 🎁",
          url: permanentUrl,
        });
      } catch (err) {
        // User cancelled or unsupported
      }
    } else {
      handleCopy();
    }
  };

  const whatsappUrl = `https://api.whatsapp.com/send?text=${encodeURIComponent(
    `Hey! ❤️ Someone has prepared a special birthday surprise for you 🎁✨ Open it here: ${permanentUrl}`
  )}`;

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/60 backdrop-blur-sm animate-fade-in">
      <div className="bg-white rounded-3xl p-6 sm:p-7 max-w-md w-full shadow-2xl border-2 border-rose-100 max-h-[90vh] overflow-y-auto">
        <div className="flex items-center justify-between pb-3 border-b border-rose-100 mb-4">
          <div className="flex items-center gap-2">
            <Share2 className="w-5 h-5 text-rose-600" />
            <h3 className="font-serif-romantic text-xl font-bold text-rose-950">
              Shareable Permanent Link 🔗
            </h3>
          </div>
          <button
            onClick={onClose}
            className="w-8 h-8 rounded-full hover:bg-rose-50 text-rose-400 hover:text-rose-600 flex items-center justify-center transition text-lg"
          >
            ×
          </button>
        </div>

        {/* Permanent Status Badge */}
        <div className="bg-emerald-50 border border-emerald-200 rounded-2xl p-3.5 mb-4 flex items-start gap-2.5">
          <ShieldCheck className="w-5 h-5 text-emerald-600 shrink-0 mt-0.5" />
          <div className="text-xs text-emerald-950">
            <strong className="font-semibold block text-emerald-900">
              24/7 Always Active Link (हमेशा काम करेगा)
            </strong>
            यह लिंक Cloud Run / CDN पर हमेशा लाइव रहता है। आपका कंप्यूटर या फोन बंद होने पर भी कोई भी इसे कभी भी खोल सकता है।
          </div>
        </div>

        {/* The Link Box */}
        <div className="mb-4">
          <label className="block text-xs font-semibold text-rose-900 mb-1.5">
            आपकी वेबसाइट का स्थायी लिंक (Permanent URL):
          </label>
          <div className="flex items-center gap-1.5 p-2 rounded-xl bg-rose-50/70 border border-rose-200">
            <input
              type="text"
              readOnly
              value={permanentUrl}
              className="flex-1 bg-transparent text-xs font-mono text-rose-950 outline-none select-all truncate px-1"
            />
            <button
              onClick={handleCopy}
              className={`px-3 py-1.5 rounded-lg text-xs font-semibold transition flex items-center gap-1 shadow-xs shrink-0 ${
                copied
                  ? 'bg-emerald-600 text-white'
                  : 'bg-rose-600 hover:bg-rose-700 text-white'
              }`}
            >
              {copied ? (
                <>
                  <Check className="w-3.5 h-3.5" />
                  <span>Copied!</span>
                </>
              ) : (
                <>
                  <Copy className="w-3.5 h-3.5" />
                  <span>Copy Link</span>
                </>
              )}
            </button>
          </div>
        </div>

        {/* Quick Send Options */}
        <div className="space-y-2 mb-5">
          <a
            href={whatsappUrl}
            target="_blank"
            rel="noopener noreferrer"
            className="w-full py-2.5 px-4 rounded-xl bg-[#25D366] hover:bg-[#20ba59] text-white font-semibold text-sm flex items-center justify-center gap-2 shadow-sm transition"
          >
            <span>Share on WhatsApp 💬</span>
            <ExternalLink className="w-4 h-4" />
          </a>

          {typeof navigator !== 'undefined' && 'share' in navigator && (
            <button
              onClick={handleShareNative}
              className="w-full py-2.5 px-4 rounded-xl bg-rose-100 hover:bg-rose-200 text-rose-900 font-semibold text-sm flex items-center justify-center gap-2 transition"
            >
              <Share2 className="w-4 h-4 text-rose-600" />
              <span>Share via Phone Apps (Instagram / Messages)</span>
            </button>
          )}
        </div>

        {/* Free Hosting Tips */}
        <div className="p-3.5 rounded-2xl bg-amber-50/80 border border-amber-200 text-[11px] text-amber-950 leading-relaxed">
          <div className="font-bold flex items-center gap-1 text-amber-900 mb-1">
            <Sparkles className="w-3.5 h-3.5 text-amber-600" />
            <span>लाइफटाइम फ्री कस्टम डोमेन ऑप्शन:</span>
          </div>
          यदि आप अपनी खुद की लिंक (जैसे <code>priya-birthday.vercel.app</code> या <code>.netlify.app</code>) चाहते हैं, तो आप इस पूरे कोड को 1-क्लिक में <strong>Vercel, Netlify या GitHub Pages</strong> पर भी 100% फ्री में हमेशा के लिए होस्ट कर सकते हैं।
        </div>
      </div>
    </div>
  );
};
