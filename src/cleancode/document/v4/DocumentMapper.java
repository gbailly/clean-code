package cleancode.document.v4;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;

import static java.util.Objects.isNull;

@RequiredArgsConstructor
@Component
public class DocumentMapper {

    private static final String MEDIA_TYPE_PDF = MediaType.APPLICATION_PDF_VALUE;

    private final AttachmentStore attachmentStore;
    private final AttachmentFactory attachmentFactory;

    @Nullable
    public Document map(@Nonnull final Long userId, @Nonnull final LocalDate date, @Nullable final AttachmentDto attachmentDto) {
        if (isNull(attachmentDto)) {
            return null;
        }
        return new Document(userId, date, map(attachmentDto));
    }

    private Attachment map(@NonNull final AttachmentDto attachmentDto) {
        final var storeId = attachmentStore.upload(new ByteArrayInputStream(attachmentDto.fileContent()), MEDIA_TYPE_PDF);
        return attachmentFactory.create(storeId, attachmentDto.fileName(), MEDIA_TYPE_PDF);
    }

    public record AttachmentDto(
            byte[] fileContent,
            String fileName
    ) {
    }
}
