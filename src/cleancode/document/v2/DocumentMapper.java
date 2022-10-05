package cleancode.document.v2;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;

import static java.util.Objects.isNull;

@RequiredArgsConstructor
@Component
public class DocumentMapper {

    private final AttachmentStore attachmentStore;
    private final AttachmentFactory attachmentFactory;

    @Nullable
    public Document map(@Nonnull final Long userId, @Nonnull final LocalDate date, @Nullable final AttachmentDto attachmentDto) {
        if (isNull(attachmentDto)) {
            return null;
        }

        final var storeId = attachmentStore.upload(
                new ByteArrayInputStream(attachmentDto.fileContent()),
                MediaType.APPLICATION_PDF_VALUE
        );
        final var attachment = attachmentFactory.create(
                storeId,
                attachmentDto.fileName(),
                MediaType.APPLICATION_PDF_VALUE
        );
        return new Document(userId, date, attachment);
    }

    public record AttachmentDto(
            byte[] fileContent,
            String fileName
    ) {
    }
}
