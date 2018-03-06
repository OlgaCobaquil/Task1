/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Image;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author olgac
 */


/*
 Pasos de discretizacion: 
 1. tomar imagen y hacer resice al tamanio n
 2. de esta imagen evaluar los colores RGB 
 3. por cada color encontrado por medio de rango RGB definir el color (Graph)
 4. pintar la discretización 
 
*/
public class ImageDiscretizer {
    private int Size;
    private BufferedImage ImageDiscretized;
    
    public ImageDiscretizer(File originalImage, int size) {
        this.Size = size;
        
        try {
            BufferedImage originalBufferedImage = ImageIO.read(originalImage);
            this.ImageDiscretized = Discretize(originalBufferedImage);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public BufferedImage Discretize(BufferedImage originalImage) {
        BufferedImage resizedImage = new BufferedImage(this.Size, this.Size, BufferedImage.TYPE_INT_ARGB);
	Graphics2D g = resizedImage.createGraphics();
        
	g.drawImage(originalImage, 0, 0, this.Size, this.Size, null);
	g.dispose();
	g.setComposite(AlphaComposite.Src);

	g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        return resizedImage;
    }
  
    public BufferedImage getImageDiscretized() {
        return ImageDiscretized;
    }
}
