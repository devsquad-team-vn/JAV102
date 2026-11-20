/* PolyCoffee Shared Client Utilities: WebSocket, Currency Formatter, Toast, Cash Calculator */
(function (window) {
    'use strict';

    window.PolyCoffee = {
        formatVnd: function (num) {
            const val = Math.max(0, Math.round(Number(num) || 0));
            return val.toLocaleString('vi-VN') + 'đ';
        },

        showToast: function (title, message, variant) {
            let stack = document.getElementById('pcToastStack');
            if (!stack) {
                stack = document.createElement('div');
                stack.id = 'pcToastStack';
                stack.className = 'pc-toast-container';
                document.body.appendChild(stack);
            }
            const tone = variant || 'warning';
            const el = document.createElement('div');
            el.className = 'toast show align-items-center text-bg-' + tone + ' border-0 shadow';
            el.setAttribute('role', 'alert');
            el.innerHTML =
                '<div class="d-flex">' +
                '  <div class="toast-body"><strong>' + title + '</strong><div class="small">' + message + '</div></div>' +
                '  <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>' +
                '</div>';
            stack.appendChild(el);
            setTimeout(function () {
                if (el.parentNode) el.parentNode.removeChild(el);
            }, 6000);
        },

        playChime: function () {
            try {
                const AudioCtx = window.AudioContext || window.webkitAudioContext;
                if (!AudioCtx) return;
                const ctx = new AudioCtx();
                const osc = ctx.createOscillator();
                const gain = ctx.createGain();
                osc.type = 'sine';
                osc.frequency.setValueAtTime(880, ctx.currentTime);
                osc.frequency.setValueAtTime(1174, ctx.currentTime + 0.11);
                gain.gain.setValueAtTime(0.08, ctx.currentTime);
                gain.gain.exponentialRampToValueAtTime(0.001, ctx.currentTime + 0.35);
                osc.connect(gain);
                gain.connect(ctx.destination);
                osc.start();
                osc.stop(ctx.currentTime + 0.36);
            } catch (e) {
                // Audio context blocked by browser autoplay policy
            }
        },

        connectOrderSocket: function (contextPath, onEvent, onStatusChange) {
            const proto = window.location.protocol === 'https:' ? 'wss://' : 'ws://';
            const url = proto + window.location.host + (contextPath || '') + '/ws/orders';
            let ws;
            try {
                ws = new WebSocket(url);
                ws.onopen = function () {
                    if (onStatusChange) onStatusChange(true);
                };
                ws.onclose = function () {
                    if (onStatusChange) onStatusChange(false);
                };
                ws.onmessage = function (ev) {
                    try {
                        const data = JSON.parse(ev.data);
                        if (onEvent) onEvent(data);
                    } catch (e) {
                        // Ignore malformed frames
                    }
                };
            } catch (e) {
                if (onStatusChange) onStatusChange(false);
            }
            return ws;
        }
    };

    // Chong double-submit khi dang load (khach hoac thu ngan bam nhieu lan khi POST chua xong)
    document.addEventListener('DOMContentLoaded', function () {
        document.addEventListener('submit', function (e) {
            if (e.defaultPrevented) {
                return;
            }
            const form = e.target;
            if (!form || !form.method || form.method.toLowerCase() !== 'post') {
                return;
            }
            if (form.dataset.allowMulti === 'true') {
                return;
            }
            if (form.dataset.submitting === 'true') {
                e.preventDefault();
                return;
            }
            if (e.submitter && e.submitter.name) {
                let hiddenSub = form.querySelector('input[data-pc-submitter="1"]');
                if (!hiddenSub) {
                    hiddenSub = document.createElement('input');
                    hiddenSub.type = 'hidden';
                    hiddenSub.dataset.pcSubmitter = '1';
                    form.appendChild(hiddenSub);
                }
                hiddenSub.name = e.submitter.name;
                hiddenSub.value = e.submitter.value || '';
            }
            form.dataset.submitting = 'true';
            setTimeout(function () {
                form.querySelectorAll('button[type="submit"], input[type="submit"]').forEach(function (btn) {
                    if (!btn.disabled) {
                        btn.dataset.origHtml = btn.innerHTML;
                        btn.disabled = true;
                        if (btn.innerHTML.indexOf('spinner-border') === -1 && btn.innerText.trim().length > 1) {
                            btn.innerHTML = '<span class="spinner-border spinner-border-sm me-1"></span>Đang xử lý...';
                        }
                    }
                });
            }, 0);
            // Mo khoa phong truong hop form bi chan boi validation / tab khac sau 8s
            setTimeout(function () {
                form.dataset.submitting = 'false';
                form.querySelectorAll('button[type="submit"], input[type="submit"]').forEach(function (btn) {
                    if (btn.dataset.origHtml) {
                        btn.disabled = false;
                        btn.innerHTML = btn.dataset.origHtml;
                    }
                });
            }, 8000);
        });
    });

    window.addEventListener('pageshow', function () {
        document.querySelectorAll('form[data-submitting="true"]').forEach(function (form) {
            form.dataset.submitting = 'false';
            form.querySelectorAll('button[type="submit"], input[type="submit"]').forEach(function (btn) {
                if (btn.dataset.origHtml) {
                    btn.disabled = false;
                    btn.innerHTML = btn.dataset.origHtml;
                }
            });
        });
    });
})(window);
