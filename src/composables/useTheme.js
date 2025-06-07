
export function useTheme(){

  const theme = {
    green: getComputedStyle(document.documentElement).getPropertyValue('--green-forest'),
    blue: getComputedStyle(document.documentElement).getPropertyValue('--blue-river'),
    brown: getComputedStyle(document.documentElement).getPropertyValue('--brown-earth'),
    red: getComputedStyle(document.documentElement).getPropertyValue('--earth-red'),
    gold: getComputedStyle(document.documentElement).getPropertyValue('--gold-sun')

  };

  return { theme };
}
