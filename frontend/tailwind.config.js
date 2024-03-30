/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{html,ts}",
  ],
  theme: {
    screens:{
      'sm': '479px',
      'md': '767px',
      'lg': '930px'
    },
    extend: {
      colors: {
        'button_blue': '#00b6ef',
        'dark-blue': '#398dca',
        'placeholder': '#8abfe2',
        'grey_selected': '#9b9b9b',
      },
      fontFamily: {
        helvetica: ['Helvetica', 'sans-serif'],
        average_sans: ['Average Sans', 'sans-serif']
      },
    },
  },
  plugins: [],
}