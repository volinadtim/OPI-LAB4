const xInputValue = #{rifle.coordinateXInput.value};
const yInputValue = #{rifle.coordinateYInput.value};
const rInputValue = #{rifle.radiusInput.value};

const xInputMin = #{rifle.coordinateXInput.min};
const xInputMax = #{rifle.coordinateXInput.max};
const yInputMin = #{rifle.coordinateYInput.min};
const yInputMax = #{rifle.coordinateYInput.max};
const rInputMin = #{rifle.radiusInput.min};
const rInputMax = #{rifle.radiusInput.max};


const GRAPH = [
    {
        shape: '#{rifle.target.quadrant1 != null ? rifle.target.quadrant1.name : 'None'}',
        width: #{rifle.target.quadrant1 != null ? rifle.target.quadrant1.width * 2 : 0 },
        height: #{rifle.target.quadrant1 != null ? rifle.target.quadrant1.height * 2 : 0 },
    },
    {
        shape: '#{rifle.target.quadrant2 != null ? rifle.target.quadrant2.name : 'None'}',
        width: #{rifle.target.quadrant2 != null ? rifle.target.quadrant2.width * 2 : 0 },
        height: #{rifle.target.quadrant2 != null ? rifle.target.quadrant2.height * 2 : 0 },
    },
    {
        shape: '#{rifle.target.quadrant3 != null ? rifle.target.quadrant3.name : 'None'}',
        width: #{rifle.target.quadrant3 != null ? rifle.target.quadrant3.width * 2 : 0 },
        height: #{rifle.target.quadrant3 != null ? rifle.target.quadrant3.height * 2 : 0 },
    },
    {
        shape: '#{rifle.target.quadrant4 != null ? rifle.target.quadrant4.name : 'None'}',
        width: #{rifle.target.quadrant4 != null ? rifle.target.quadrant4.width * 2 : 0 },
        height: #{rifle.target.quadrant4 != null ? rifle.target.quadrant4.height * 2 : 0 },
    },
]
