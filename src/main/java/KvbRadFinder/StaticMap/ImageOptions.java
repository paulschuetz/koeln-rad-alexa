package KvbRadFinder.StaticMap;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ImageOptions {
    private Size size;

    public ImageOptions withSize(Size size) {
        this.size = size;
        return this;
    }
}
