const interval = setInterval(() => {
  const audio = document.querySelector("audio");
  if (audio) {
    audio.preservesPitch = false;
    console.log("Done!")
    clearInterval(interval);
  }
}, 100);